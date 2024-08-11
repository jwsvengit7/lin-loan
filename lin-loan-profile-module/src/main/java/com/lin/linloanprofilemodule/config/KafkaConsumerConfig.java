package com.lin.linloanprofilemodule.config;


import com.lin.linloanprofilemodule.config.listener.KafkaProfileConsumerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID;
    @Value("${spring.kafka.bootstrap-servers}")
    private String KAFKA_URL;

    @Bean
    public ConsumerFactory<String, Object> linLoanConsumerFactory() {
        Map<String, Object> linLoanConfigurationProperties = new HashMap<>();
        linLoanConfigurationProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URL);
        linLoanConfigurationProperties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        linLoanConfigurationProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        linLoanConfigurationProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        linLoanConfigurationProperties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        linLoanConfigurationProperties.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
        linLoanConfigurationProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        linLoanConfigurationProperties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaProfileConsumerService.class.getName());

        return new DefaultKafkaConsumerFactory<>(linLoanConfigurationProperties);
    }

}
