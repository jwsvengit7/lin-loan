package com.lin.linloanauthmodule.services;

import com.lin.commonsshared.model.request.OtpData;
import com.lin.commonsshared.model.request.ProfileDtoData;
import com.lin.linloanauthmodule.messaging.KafkaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.lin.commonsshared.utils.MessageUtils.OTP_TOPIC;
import static com.lin.commonsshared.utils.MessageUtils.PROFILE_TOPIC;
@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingServiceImpl.class);

    private final KafkaService publisher;

    @Override
    public void messagePublisher(OtpData userOTP){
        HashMap<Object,Object> data = new HashMap<>();
        data.put("OTP", userOTP.getOtp());
        data.put("email", userOTP.getEmail());
        data.put("id",userOTP.getId().toString());
        LOGGER.info("<<<<<<<<  Sending OTP Message from >>>>>>>{}", MessagingServiceImpl.class.getName());
        publisher.sendMessage(data,OTP_TOPIC);
    }
    @Override
    public void messagePublisherProfile(ProfileDtoData profileData){
        LOGGER.info("<<<<<<<<  Sending PROFILE Message from >>>>>>>{}", MessagingServiceImpl.class.getName());
        publisher.sendMessage(profileData,PROFILE_TOPIC);
    }

}
