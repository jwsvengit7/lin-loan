package com.lin.commons.exceptions.handler;

import com.lin.commons.exceptions.message.GlobalMessage;
import com.lin.commonsshared.model.response.LinLoanResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(GlobalMessage.class)
    public LinLoanResponse<GlobalMessage> errorMessage(GlobalMessage e){
        GlobalMessage message = new GlobalMessage(e.getMessage(),e.getStatus());
        message.setMessage(e.getMessage());
        message.setTime(LocalDateTime.now().toString());
        message.setStatus(e.getStatus());
        return new LinLoanResponse<>(message);

    }
}
