package com.nataliya.util;

import com.nataliya.dto.NewMatchDto;
import com.nataliya.exception.InvalidRequestException;

import java.util.UUID;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void validate(NewMatchDto newMatchDto) {

        String player1Name = newMatchDto.getPlayer1Name();
        String player2Name = newMatchDto.getPlayer2Name();

        if (isParameterMissed(player1Name)) {
            throw new InvalidRequestException("Invalid request: Missing Player one name");
        }
        if (isParameterMissed(player2Name)) {
            throw new InvalidRequestException("Invalid request: Missing Player two name");
        }
        if (isNameInvalid(player1Name) && isNameInvalid(player2Name)) {
            throw new InvalidRequestException("Invalid players names. Name: up to 20 characters - Latin letters or numbers");
        }
        if (isNameInvalid(player1Name)) {
            throw new InvalidRequestException("Invalid Player one name. Name: up to 20 characters - Latin letters or numbers");
        }
        if (isNameInvalid(player2Name)) {
            throw new InvalidRequestException("Invalid Player two name. Name: up to 20 characters - Latin letters or numbers");
        }
        if (player1Name.equals(player2Name)) {
            throw new InvalidRequestException("Invalid request: Player names must be different");
        }
    }

    public static UUID getValidUuid(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Bad request: Invalid or missed UUID", e);
        }
    }

    public static long getValidPointWinnerId(String pointWinnerId) {
        if (isParameterMissed(pointWinnerId)) {
            throw new InvalidRequestException("Invalid operation: Missing player winner parameter");
        }
        try {
            return Long.parseLong(pointWinnerId);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid parameter: point winner id is not a number", e);
        }
    }

    public static int getValidPageNumber(String page) {
        try {
            int pageNumber = Integer.parseInt(page);
            return pageNumber >= 0 ? pageNumber : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private static boolean isParameterMissed(String parameter) {
        return parameter == null || parameter.isBlank();
    }

    private static boolean isNameInvalid(String name) {
        return name.length() > 20 || !name.matches("[a-zA-Z0-9 ]+");
    }
}
