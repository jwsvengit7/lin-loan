package com.lin.linloanauthmodule.services;

import com.lin.commons.model.request.OtpData;
import com.lin.commons.model.request.ProfileDtoData;

;

public interface MessagingService {
    void messagePublisherProfile(ProfileDtoData profileData);
    void messagePublisher(OtpData userOTP);
}
