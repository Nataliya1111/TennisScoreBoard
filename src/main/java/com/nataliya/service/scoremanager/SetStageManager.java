package com.nataliya.service.scoremanager;

import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.PlayerScore;

public class SetStageManager extends ScoreStageManager{

    public SetStageManager(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch) {
        super(pointWinnerScore, pointLoserScore, ongoingMatch);
    }

    @Override
    public boolean isStageComplete() {
        int pointWinnerGames = pointWinnerScore.getGames();
        int pointLoserGames = pointLoserScore.getGames();

        if(pointWinnerGames < 6){
            return false;
        }
        if(pointWinnerGames == 6){
            return pointWinnerGames - pointLoserGames >=2;
        }
        if(pointWinnerGames == 7){
            return true;
        }
        throw new InvalidStageStateException("Player can't win more than 7 games");
    }

    @Override
    public void handleStage() {
        pointWinnerScore.incrementSets();
        pointWinnerScore.setGamesToZero();
        pointLoserScore.setGamesToZero();
    }
}
