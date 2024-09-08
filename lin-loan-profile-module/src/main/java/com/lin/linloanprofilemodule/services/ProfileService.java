package com.lin.linloanprofilemodule.services;

import com.lin.commonsshared.model.request.ProfileDtoData;

public interface ProfileService {

    void receivedFromKafkaSaveProfile(ProfileDtoData data);
}
