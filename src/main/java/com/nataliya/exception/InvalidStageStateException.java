package com.nataliya.exception;

import jakarta.servlet.http.HttpServletResponse;

public class InvalidStageStateException extends CustomException{

    private static final int STATUS_CODE = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    public InvalidStageStateException(String message) {
        super(message, STATUS_CODE);
    }

    public InvalidStageStateException(String message, Throwable cause) {
        super(message, cause, STATUS_CODE);
    }
}
