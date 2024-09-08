package com.lin.commons.helpers;

import com.lin.commonsshared.model.response.LinLoanResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Helpers {

    public static <T> ResponseEntity<LinLoanResponse<T>> linLoanresponseEntity(T data,String message, HttpStatus status) {
        return new ResponseEntity<>(new LinLoanResponse<>(data,message), status);
    }
    public static <T> ResponseEntity<LinLoanResponse<T>> linLoanresponseEntity(T data, HttpStatus status) {
        return new ResponseEntity<>(new LinLoanResponse<>(data), status);
    }
    public static <T> ResponseEntity<LinLoanResponse<T>> linLoanresponseEntity(HttpStatus status) {
        return new ResponseEntity<>(new LinLoanResponse<>(), status);
    }
}


