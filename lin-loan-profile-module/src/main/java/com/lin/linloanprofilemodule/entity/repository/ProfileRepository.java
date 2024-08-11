package com.lin.linloanprofilemodule.entity.repository;

import com.lin.linloanprofilemodule.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;

public interface ProfileRepository extends MongoRepository<Profile,String> {
    Profile findByUserId(String id);
    boolean existsByEmail(String email);
    List<Profile> findAllByPhone(Long phone);
}
