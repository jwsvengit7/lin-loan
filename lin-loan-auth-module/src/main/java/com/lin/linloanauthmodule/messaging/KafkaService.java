package com.lin.linloanauthmodule.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component

public class KafkaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);
    @Autowired
    private  KafkaTemplate<String,Object> message;
    @Autowired
    private  ObjectMapper mapper;
        @Value("${spring.kafka.consumer.group-id}")
        private String groupId;

    public KafkaService() {

    }

    public void sendMessage(Object data,String topic){
        try {
            message.send(topic, mapper.writeValueAsString(data));
            LOGGER.info("<<<<<<<< Message Sent >>>>>>>{}", KafkaService.class);
        }
        catch (Exception e){
            LOGGER.info("<<<<<<<< Error occur {} >>>>>>>{}", e.getMessage(), KafkaService.class);
        }

    }


}
