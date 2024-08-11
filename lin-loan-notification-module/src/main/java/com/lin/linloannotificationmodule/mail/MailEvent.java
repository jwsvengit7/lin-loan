package com.lin.linloannotificationmodule.mail;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

@Getter
public class MailEvent extends ApplicationEvent {

    private HashMap<String,String> data;
    public MailEvent(HashMap<String, String> data){
        super(data);
        this.data=data;
    }

}
