package com.nataliya.service.scoremanager;

import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.PlayerScore;

public class SetStageManager extends ScoreStageManager {

    private static final int GAMES_FOR_SET_WIN = 6;
    private static final int WIN_GAMES_DIFFERENCE = 2;
    private static final int GAMES_FOR_ABSOLUTE_SET_WIN = 7;

    public SetStageManager(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch) {
        super(pointWinnerScore, pointLoserScore, ongoingMatch);
    }

    @Override
    public boolean isStageComplete() {
        int pointWinnerGames = pointWinnerScore.getGames();
        int pointLoserGames = pointLoserScore.getGames();

        if (pointWinnerGames < GAMES_FOR_SET_WIN) {
            return false;
        }
        if (pointWinnerGames == GAMES_FOR_SET_WIN) {
            return pointWinnerGames - pointLoserGames >= WIN_GAMES_DIFFERENCE;
        }
        if (pointWinnerGames == GAMES_FOR_ABSOLUTE_SET_WIN) {
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
