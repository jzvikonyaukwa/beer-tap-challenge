package com.rviewer.skeletons.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DispenserStatusException extends RuntimeException{

    private final int code;
    public DispenserStatusException(int code, String message,  Exception ex) {
        super(message, ex);
        this.code = code;
    }

    public DispenserStatusException(int code, String message) {
        this(code,message,null);
    }
}
