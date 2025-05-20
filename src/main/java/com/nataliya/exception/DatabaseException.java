package com.nataliya.exception;

import jakarta.servlet.http.HttpServletResponse;

public class DatabaseException extends CustomException{

    private static final int STATUS_CODE = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    public DatabaseException(String message, Throwable cause) {
        super(message, cause, STATUS_CODE);
    }

    public DatabaseException(String message) {
        super(message, STATUS_CODE);
    }
}
