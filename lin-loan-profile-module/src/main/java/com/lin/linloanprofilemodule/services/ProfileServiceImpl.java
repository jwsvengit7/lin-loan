package com.lin.linloanprofilemodule.services;

import com.lin.commonsshared.model.request.ProfileDtoData;
import com.lin.linloanprofilemodule.entity.Profile;
import com.lin.linloanprofilemodule.entity.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProfileServiceImpl implements ProfileService{
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void receivedFromKafkaSaveProfile(ProfileDtoData data) {
        Profile profile = profileRepository.findByUserId(data.getUserId());
        if (Objects.nonNull(profile)){
            throw new RuntimeException();
        }
        boolean exist = profileRepository.existsByEmail(data.getEmail());
        if (exist){
            throw new RuntimeException();
        }
        Profile newProfile = new Profile();
        newProfile.setBvn(data.getBvn());
        newProfile.setEmail(data.getEmail());
        newProfile.setFirstName(data.getFirstName());
        newProfile.setLastName(data.getLastName());
        newProfile.setRole(data.getRole().name());
        newProfile.setNin(data.getNin());
        newProfile.setPhone(data.getPhone());
        profileRepository.insert(newProfile);


    }

}
