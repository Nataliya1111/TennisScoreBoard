package com.nataliya.exception;

import jakarta.servlet.http.HttpServletResponse;

public class InvalidRequestException extends CustomException {

    private static final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;

    public InvalidRequestException(String message) {
        super(message, STATUS_CODE);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause, STATUS_CODE);
    }
}
