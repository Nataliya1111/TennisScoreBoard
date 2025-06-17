package com.nataliya.service.scoremanager;

import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.MatchState;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.PlayerScore;
import com.nataliya.model.Points;

public class GameStageManager extends ScoreStageManager {

    private static final int TIE_BREAK_WIN_POINTS = 7;
    private static final int WIN_POINTS_DIFFERENCE = 2;

    private static final int GAMES_FOR_TIE_BREAK = 6;

    public GameStageManager(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch) {
        super(pointWinnerScore, pointLoserScore, ongoingMatch);
    }

    @Override
    public boolean isStageComplete() {
        return switch (ongoingMatch.getMatchState()) {
            case ONGOING -> pointWinnerScore.getPoints() == Points.GAME;
            case TIE_BREAK -> {
                if (pointWinnerScore.getTieBreakPoints() < TIE_BREAK_WIN_POINTS) {
                    yield false;
                }
                yield pointWinnerScore.getTieBreakPoints() - pointLoserScore.getTieBreakPoints() >= WIN_POINTS_DIFFERENCE;
            }
            case FINISHED ->
                    throw new InvalidStageStateException("Invalid operation: match is already in FINISHED state");
        };
    }

    @Override
    public void handleStage() {
        pointWinnerScore.incrementGames();

        switch (ongoingMatch.getMatchState()) {
            case ONGOING -> {
                pointWinnerScore.setPointsToZero();
                pointLoserScore.setPointsToZero();
                if (pointWinnerScore.getGames() == GAMES_FOR_TIE_BREAK && pointLoserScore.getGames() == GAMES_FOR_TIE_BREAK) {
                    ongoingMatch.setMatchState(MatchState.TIE_BREAK);
                }
            }
            case TIE_BREAK -> {
                pointWinnerScore.setTieBreakPointsToZero();
                pointLoserScore.setTieBreakPointsToZero();
                ongoingMatch.setMatchState(MatchState.ONGOING);
            }
            case FINISHED ->
                    throw new InvalidStageStateException("Invalid operation: match is already in FINISHED state");
        }
    }
}
