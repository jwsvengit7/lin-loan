package com.lin.commonsshared.utils;

import lombok.Getter;

import java.util.UUID;
@Getter
public class Utils {
    public static String generateOtp(){
        return UUID.randomUUID().toString().substring(0,4);
    }
}
