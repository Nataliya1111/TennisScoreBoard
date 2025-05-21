package com.nataliya.exception;

import jakarta.servlet.http.HttpServletResponse;

public class NotFoundException extends CustomException{

    private static final int STATUS_CODE = HttpServletResponse.SC_NOT_FOUND;

    public NotFoundException(String message) {
        super(message, STATUS_CODE);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause, STATUS_CODE);
    }
}
