package com.lin.linloannotificationmodule.config.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.linloannotificationmodule.mail.MailEvent;
import com.lin.linloannotificationmodule.mail.MailServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static com.lin.commonsshared.utils.MessageUtils.OTP_TOPIC;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final MailServiceImpl service;
    private final ObjectMapper mapper;

    public KafkaConsumerService(MailServiceImpl service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = OTP_TOPIC)
    public void processSignupMessage(String content) {
        LOGGER.info("<<<<<<<<<<<<  Receiving Message {} >>>>>>>>>>>> {}", content, KafkaConsumerService.class.getName());

        try {
            HashMap<String, String> data = mapper.readValue(content, new TypeReference<HashMap<String, String>>() {});
            LOGGER.info("<<<<<<<<<<<<  Deserialized Data {} >>>>>>>>>>>> {}", data, KafkaConsumerService.class.getName());
            MailEvent event = new MailEvent(data);
            service.onApplicationEvent(event);

        } catch (Exception e) {
            LOGGER.error("Error deserializing message: {}", e.getMessage());
        }
    }
}
