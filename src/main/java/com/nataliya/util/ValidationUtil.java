package com.nataliya.util;

import com.nataliya.dto.NewMatchDto;
import com.nataliya.exception.InvalidRequestException;

public class ValidationUtil {

    private ValidationUtil(){
    }

    public static void validate(NewMatchDto newMatchDto){

        String player1Name = newMatchDto.getPlayer1Name();
        String player2Name = newMatchDto.getPlayer1Name();

        if (player1Name == null || player1Name.isBlank()){
            throw new InvalidRequestException("Missing player one name");
        }
        if (player2Name == null || player2Name.isBlank()){
            throw new InvalidRequestException("Missing player two name");
        }
        if(player1Name.length() > 20 || !player1Name.matches("[a-zA-Z0-9 ]")){
            throw new InvalidRequestException("Invalid player one name. Name: up to 20 characters - Latin letters or numbers");
        }
        if(player1Name.equals(player2Name)){
            throw new InvalidRequestException("Player names must be different");
        }
    }


}
