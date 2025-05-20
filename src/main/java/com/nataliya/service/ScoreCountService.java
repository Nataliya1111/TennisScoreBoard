package com.nataliya.service;

import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.*;
import com.nataliya.service.scoremanager.GameStageManager;
import com.nataliya.service.scoremanager.MatchStageManager;
import com.nataliya.service.scoremanager.ScoreStageManager;
import com.nataliya.service.scoremanager.SetStageManager;

import java.util.List;

public class ScoreCountService {

    public Score updateScore(OngoingMatch match, Long pointWinnerId){

        Score score = match.getScore();
        PlayerScore player1Score = score.getPlayer1Score();
        PlayerScore player2Score = score.getPlayer2Score();

        if (pointWinnerId.equals(match.getPlayer1().getId())){
            updatePlayersScores(player1Score, player2Score, match);
        }
        else if (pointWinnerId.equals(match.getPlayer2().getId())) {
            updatePlayersScores(player2Score, player1Score, match);
        }
        else throw new InvalidStageStateException("Invalid operation: Point winner ID does not match any player in the match");
        return score;
    }

    private void updatePlayersScores(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch){

        switch (ongoingMatch.getMatchState()){
            case ONGOING -> updatePoints(pointWinnerScore, pointLoserScore);
            case TIE_BREAK -> pointWinnerScore.incrementTieBreakPoints();
            case FINISHED -> throw new InvalidStageStateException("Invalid operation: match is already in FINISHED state");
        }

        GameStageManager gameManager = new GameStageManager(pointWinnerScore,pointLoserScore,ongoingMatch);
        SetStageManager setManager = new SetStageManager(pointWinnerScore, pointLoserScore, ongoingMatch);
        MatchStageManager matchManager = new MatchStageManager(pointWinnerScore, pointLoserScore, ongoingMatch);
        List<ScoreStageManager> scoreStageManagers = List.of(gameManager, setManager, matchManager);
        for(ScoreStageManager scoreStageManager : scoreStageManagers){
            if (!scoreStageManager.isStageComplete()){
                break;
            }
            scoreStageManager.handleStage();
        }
    }

    private void updatePoints(PlayerScore pointWinnerScore, PlayerScore pointLoserScore) {
        Points winnerPoints = pointWinnerScore.getPoints();
        Points loserPoints = pointLoserScore.getPoints();
        switch (winnerPoints){
            case LOVE -> winnerPoints = Points.FIFTEEN;
            case FIFTEEN -> winnerPoints = Points.THIRTY;
            case THIRTY -> {
                if (loserPoints == Points.FORTY){
                    winnerPoints = Points.DEUCE;
                    loserPoints = Points.DEUCE;
                } else {
                    winnerPoints = Points.FORTY;
                }
            }
            case FORTY -> {
                if (loserPoints == Points.ADVANTAGE){
                    winnerPoints = Points.DEUCE;
                    loserPoints = Points.DEUCE;
                }
                else{
                    winnerPoints = Points.GAME;
                }
            }
            case DEUCE -> {
                winnerPoints = Points.ADVANTAGE;
                loserPoints = Points.FORTY;
            }
            case ADVANTAGE -> winnerPoints = Points.GAME;
            case GAME -> throw new InvalidStageStateException("Invalid operation: Cannot score after game is already won");
        }
        pointWinnerScore.setPoints(winnerPoints);
        pointLoserScore.setPoints(loserPoints);
    }

}