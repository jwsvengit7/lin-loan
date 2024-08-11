package com.lin.linloanprofilemodule.services;

import com.lin.commons.model.request.ProfileDtoData;

public interface ProfileService {

    void receivedFromKafkaSaveProfile(ProfileDtoData data);
}
