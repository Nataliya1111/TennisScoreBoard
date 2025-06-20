package com.nataliya.service;

import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.*;
import com.nataliya.service.scoremanager.GameStageManager;
import com.nataliya.service.scoremanager.MatchStageManager;
import com.nataliya.service.scoremanager.ScoreStageManager;
import com.nataliya.service.scoremanager.SetStageManager;

import java.util.List;

public class ScoreCountService {

    public Score updateScore(OngoingMatch match, Long pointWinnerId) {

        Score score = match.getScore();
        PlayerScore player1Score = score.getPlayer1Score();
        PlayerScore player2Score = score.getPlayer2Score();

        if (pointWinnerId.equals(match.getPlayer1().getId())) {
            updatePlayersScores(player1Score, player2Score, match);
            match.setLastPointWinner(match.getPlayer1());
        } else if (pointWinnerId.equals(match.getPlayer2().getId())) {
            updatePlayersScores(player2Score, player1Score, match);
            match.setLastPointWinner(match.getPlayer2());
        } else
            throw new InvalidStageStateException("Invalid operation: Point winner ID does not match any player in the match");
        return score;
    }

    private void updatePlayersScores(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch) {

        switch (ongoingMatch.getMatchState()) {
            case ONGOING -> updatePoints(pointWinnerScore, pointLoserScore);
            case TIE_BREAK -> pointWinnerScore.incrementTieBreakPoints();
            case FINISHED ->
                    throw new InvalidStageStateException("Invalid operation: match is already in FINISHED state");
        }

        GameStageManager gameManager = new GameStageManager(pointWinnerScore, pointLoserScore, ongoingMatch);
        SetStageManager setManager = new SetStageManager(pointWinnerScore, pointLoserScore, ongoingMatch);
        MatchStageManager matchManager = new MatchStageManager(pointWinnerScore, pointLoserScore, ongoingMatch);
        List<ScoreStageManager> scoreStageManagers = List.of(gameManager, setManager, matchManager);
        for (ScoreStageManager scoreStageManager : scoreStageManagers) {
            if (!scoreStageManager.isStageComplete()) {
                break;
            }
            scoreStageManager.handleStage();
        }
    }

    private void updatePoints(PlayerScore pointWinnerScore, PlayerScore pointLoserScore) {
        Points winnerPoints = pointWinnerScore.getPoints();
        Points loserPoints = pointLoserScore.getPoints();
        if (isPointsStageNotValid(winnerPoints, loserPoints)) {
            throw new InvalidStageStateException(String.format("Invalid operation: points can't be %s and %s at the same time", winnerPoints, loserPoints));
        }
        switch (winnerPoints) {
            case LOVE -> winnerPoints = Points.FIFTEEN;
            case FIFTEEN -> winnerPoints = Points.THIRTY;
            case THIRTY -> {
                if (loserPoints == Points.FORTY) {
                    winnerPoints = Points.DEUCE;
                    loserPoints = Points.DEUCE;
                } else {
                    winnerPoints = Points.FORTY;
                }
            }
            case FORTY -> {
                if (loserPoints == Points.ADVANTAGE) {
                    winnerPoints = Points.DEUCE;
                    loserPoints = Points.DEUCE;
                } else {
                    winnerPoints = Points.GAME;
                }
            }
            case DEUCE -> {
                winnerPoints = Points.ADVANTAGE;
                loserPoints = Points.FORTY;
            }
            case ADVANTAGE -> winnerPoints = Points.GAME;
            case GAME ->
                    throw new InvalidStageStateException("Invalid operation: Cannot score after game is already won");
        }
        pointWinnerScore.setPoints(winnerPoints);
        pointLoserScore.setPoints(loserPoints);
    }

    private boolean isPointsStageNotValid(Points player1Points, Points player2Points) {
        if (player1Points == Points.DEUCE || player2Points == Points.DEUCE) {
            return player2Points != Points.DEUCE || player1Points != Points.DEUCE;
        }
        if (player1Points == Points.ADVANTAGE) {
            return player2Points != Points.FORTY;
        }
        if (player2Points == Points.ADVANTAGE) {
            return player1Points != Points.FORTY;
        }
        return false;
    }

}