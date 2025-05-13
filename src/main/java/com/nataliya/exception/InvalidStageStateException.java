package com.nataliya.exception;

public class InvalidStageStateException extends IllegalStateException{

    public InvalidStageStateException(String message){
        super(message);
    }

    public InvalidStageStateException(String message, Throwable cause){
        super(message, cause);
    }
}
