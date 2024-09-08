package com.lin.linloanauthmodule.services;

import com.lin.commonsshared.model.request.OtpData;
import com.lin.commonsshared.model.request.ProfileDtoData;

;

public interface MessagingService {
    void messagePublisherProfile(ProfileDtoData profileData);
    void messagePublisher(OtpData userOTP);
}
