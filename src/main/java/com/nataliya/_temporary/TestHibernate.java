package com.nataliya._temporary;

import com.nataliya.dto.NewMatchDto;
import com.nataliya.dto.ScoreDto;
import com.nataliya.model.MatchState;
import com.nataliya.model.OngoingMatch;
import com.nataliya.service.OngoingMatchService;
import com.nataliya.service.ScoreCountService;
import com.nataliya.util.MappingUtil;

import java.io.IOException;

public class TestHibernate {

    public static void main(String[] args) throws IOException {


        OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
        NewMatchDto newMatchDto = new NewMatchDto("Olga", "Ivan");

        OngoingMatch ongoingMatch = ongoingMatchService.createOngoingMatch(newMatchDto);
        System.out.println(ongoingMatch);

        NewMatchDto newMatchDto1 = new NewMatchDto("Olga2", "Ivan");

        OngoingMatch ongoingMatch1 = ongoingMatchService.createOngoingMatch(newMatchDto1);
        System.out.println(ongoingMatch1);

        ScoreCountService scoreCountService = new ScoreCountService();
        System.out.println("1" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));
        System.out.println("2" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));
        System.out.println("3" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer2().getId()));
        System.out.println("4" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));


        ScoreDto scoreDto = MappingUtil.convertToDto(ongoingMatch.getScore());
        System.out.println(scoreDto);

//        while (ongoingMatch.getMatchState() != MatchState.FINISHED){
//            scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());
//        }
//        System.out.println(ongoingMatch.getScore());
//        System.out.println(ongoingMatch.getMatchState());







        //System.out.println("App is running... Press Enter to exit");
        //System.in.read();
    }
}
