package com.lin.commons.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GlobalMessage extends RuntimeException {

    private String message;
    private String time;
    private HttpStatus status;

    public GlobalMessage(String message, HttpStatus status){
        super(message);
        this.status=status;
    }
}
