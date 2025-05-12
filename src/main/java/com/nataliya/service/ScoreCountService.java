package com.nataliya.service;

import com.nataliya.model.*;

public class ScoreCountService {

    private static final int GAMES_FOR_TIE_BREAK = 6;
    private static final int TIE_BREAK_POINTS_MAX = 7;

    public Score updateScore(OngoingMatch match, Long pointWinnerId){

        Score score = match.getScore();
        PlayerScore player1Score = score.getPlayer1Score();
        PlayerScore player2Score = score.getPlayer2Score();

        if (pointWinnerId.equals(match.getPlayer1().getId())){
            updatePlayersScores(player1Score, player2Score, match.getMatchState());
        }
        else if (pointWinnerId.equals(match.getPlayer2().getId())) {
            updatePlayersScores(player2Score, player1Score, match.getMatchState());
        }
        else throw new IllegalStateException("Point winner ID does not match any player in the match");


        return score;
    }

    private void updatePlayersScores(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, MatchState matchState){

        if (matchState == MatchState.FINISHED){
            throw new IllegalStateException("Match is already finished");
        } else if (matchState == MatchState.TIE_BREAK) {
            //updateTieBreakPoint(pointWinnerScore, pointLoserScore);
        } else {
            //updatePoints(pointWinnerScore, pointLoserScore);
        }
        updatePoints(pointWinnerScore, pointLoserScore);

        if (pointWinnerScore.getPoints() == Points.GAME){
            pointWinnerScore.incrementGames();
            pointWinnerScore.setPointsToZero();
            pointLoserScore.setPointsToZero();
        }
        if (pointWinnerScore.getGames() == GAMES_FOR_TIE_BREAK && pointLoserScore.getGames() == GAMES_FOR_TIE_BREAK){
            matchState = MatchState.TIE_BREAK;
            return;
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
            case GAME -> throw new IllegalStateException("Cannot score after game is already won");
        }
        pointWinnerScore.setPoints(winnerPoints);
        pointLoserScore.setPoints(loserPoints);
    }



    private boolean isGameWon(PlayerScore playerScore){
        return playerScore.getPoints() == Points.LOVE;
    }

}
//создать чекеры и проверять все сразу через коллекцию

//Condition - check-er isConditionsForTieBreak

//if game is finished   //isStageComplete()

//ScoreUnitsManager //MatchFinishManager //GameController
// I want to create separate classes for sets, games, match, tie-break managing - in each class will be methods isConditionCame and performActionAfterConditions. How should I name this classes, one abstract class for them and to name my methods better?