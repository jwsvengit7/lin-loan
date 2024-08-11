package com.lin.linloanauthmodule.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.linloanauthmodule.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static com.lin.commons.utils.MessageUtils.OTP_TOPIC;

@Configuration
@RequiredArgsConstructor
public class KafkaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaTemplate<String,Object> message;
    private final ObjectMapper mapper;
        @Value("${spring.kafka.consumer.group-id}")
        private String groupId;
    
        public void sendOTPMessage(Object data,String topic){
            try {
                message.send(topic, mapper.writeValueAsString(data));
                LOGGER.info("<<<<<<<< Message Sent >>>>>>>{}", KafkaService.class);
            }
            catch (Exception e){
                LOGGER.info("<<<<<<<< Error occur {} >>>>>>>{}", e.getMessage(), KafkaService.class);
            }

        }

}
