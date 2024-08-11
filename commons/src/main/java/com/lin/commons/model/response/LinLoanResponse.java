package com.lin.commons.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class LinLoanResponse<T> {
    private T data;
    private String message;
    private String time;

    public LinLoanResponse(T data,String message,String time){
        this.message=message;
        this.time=time;
        this.data=data;

    }
    public LinLoanResponse(T data,String message){
        this.message=message;
        this.time= LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d h:s"));
        this.data=data;

    }

    public LinLoanResponse(T data){
        this.message="Api Recieved";
        this.time= LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d h:s"));
        this.data=data;

    }
}
