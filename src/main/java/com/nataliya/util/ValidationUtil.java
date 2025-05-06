package com.nataliya.util;

import com.nataliya.dto.NewMatchDto;
import com.nataliya.exception.InvalidRequestException;

public class ValidationUtil {

    private ValidationUtil(){
    }

    public static void validate(NewMatchDto newMatchDto){

        String player1Name = newMatchDto.getPlayer1Name();
        String player2Name = newMatchDto.getPlayer2Name();

        if (isNameMissed(player1Name)){
            throw new InvalidRequestException("Missing Player one name");
        }
        if (isNameMissed(player2Name)){
            throw new InvalidRequestException("Missing Player two name");
        }
        if(isNameInvalid(player1Name) && isNameInvalid(player2Name)){
            throw new InvalidRequestException("Invalid players names. Name: up to 20 characters - Latin letters or numbers");
        }
        if(isNameInvalid(player1Name)){
            throw new InvalidRequestException("Invalid Player one name. Name: up to 20 characters - Latin letters or numbers");
        }
        if(isNameInvalid(player2Name)){
            throw new InvalidRequestException("Invalid Player two name. Name: up to 20 characters - Latin letters or numbers");
        }
        if(player1Name.equals(player2Name)){
            throw new InvalidRequestException("Player names must be different");
        }
    }

    private static boolean isNameMissed(String name){
        return name == null || name.isBlank();
    }

    private static boolean isNameInvalid(String name){
        return name.length() > 20 || !name.matches("[a-zA-Z0-9 ]+");
    }
}
