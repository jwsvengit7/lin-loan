package com.lin.linloanprofilemodule.config.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.commonsshared.model.request.ProfileDtoData;
import com.lin.linloanprofilemodule.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.lin.commonsshared.utils.MessageUtils.PROFILE_TOPIC;

@Service
@RequiredArgsConstructor
public class KafkaProfileConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProfileConsumerService.class);
    private final ProfileService service;
    private final ObjectMapper mapper;


    @KafkaListener(topics = PROFILE_TOPIC)
    public void processSignupMessage(String content) {
        LOGGER.info("<<<<<<<<<<<<  Receiving Message {} >>>>>>>>>>>> {}", content, KafkaProfileConsumerService.class.getName());

        try {
            ProfileDtoData data = mapper.readValue(content, ProfileDtoData.class);
            LOGGER.info("<<<<<<<<<<<<  Deserialized Data {} >>>>>>>>>>>> {}", data, KafkaProfileConsumerService.class.getName());

            service.receivedFromKafkaSaveProfile(data);

        } catch (Exception e) {
            LOGGER.error("Error deserializing message: {}  {}", e.getMessage(),KafkaProfileConsumerService.class.getName());
        }
    }
}
