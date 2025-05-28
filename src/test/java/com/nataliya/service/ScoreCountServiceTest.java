package com.nataliya.service;

import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.*;
import com.nataliya.model.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCountServiceTest {

    private static ScoreCountService scoreCountService;
    private static Player player1;
    private static Player player2;
    private PlayerScore player1Score;
    private PlayerScore player2Score;
    private OngoingMatch ongoingMatch;

    @BeforeAll
    static void init(){
        scoreCountService = new ScoreCountService();
        player1 = new Player(1L, "Player1");
        player2 = new Player(2L, "Player2");
    }

    @BeforeEach
    void prepare(){
        ongoingMatch = new OngoingMatch(player1, player2);
        Score score = ongoingMatch.getScore();
        player1Score = score.getPlayer1Score();
        player2Score = score.getPlayer2Score();
    }

    @Test
    void player1ScoresAtThirtyAgainstForty_resultsBothInDeuce(){
        player1Score.setPoints(Points.THIRTY);
        player2Score.setPoints(Points.FORTY);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(Points.DEUCE, player1Score.getPoints());
        assertEquals(Points.DEUCE, player2Score.getPoints());
    }

    @Test
    void player2ScoresAtThirtyAgainstThirty_resultsPlayer2GetsForty(){
        player1Score.setPoints(Points.THIRTY);
        player2Score.setPoints(Points.THIRTY);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer2().getId());

        assertEquals(initialPlayer1Score.getPoints(), player1Score.getPoints());
        assertEquals(Points.FORTY, player2Score.getPoints());
    }

    @Test
    void player1ScoresAtFortyAgainstThirty_resultsPlayer1WinsGame(){
        player1Score.setPoints(Points.FORTY);
        player2Score.setPoints(Points.THIRTY);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);
        PlayerScore initialPlayer2Score = getPlayerScoreCopy(player2Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getGames() + 1, player1Score.getGames());
        assertEquals(initialPlayer2Score.getGames(), player2Score.getGames());
        assertEquals(Points.LOVE, player1Score.getPoints());
        assertEquals(Points.LOVE, player2Score.getPoints());
    }

    @Test
    void player1ScoresAtFortyAgainstAdvantage_resultsBothInDeuce(){
        player1Score.setPoints(Points.FORTY);
        player2Score.setPoints(Points.ADVANTAGE);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(Points.DEUCE, player1Score.getPoints());
        assertEquals(Points.DEUCE, player2Score.getPoints());
    }

    @Test
    void Player1ScoresAtDeuce_thenGameNotFinishes(){
        player1Score.setPoints(Points.DEUCE);
        player2Score.setPoints(Points.DEUCE);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);
        PlayerScore initialPlayer2Score = getPlayerScoreCopy(player2Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getGames(), player1Score.getGames());
        assertEquals(initialPlayer2Score.getGames(), player2Score.getGames());
        assertNotEquals(Points.LOVE, player1Score.getPoints());
        assertNotEquals(Points.LOVE, player2Score.getPoints());
    }

    @Test
    void player1ScoresAtAdvantage_resultsPlayer1WinsGame(){
        player1Score.setPoints(Points.ADVANTAGE);
        player2Score.setPoints(Points.FORTY);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);
        PlayerScore initialPlayer2Score = getPlayerScoreCopy(player2Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getGames() + 1, player1Score.getGames());
        assertEquals(initialPlayer2Score.getGames(), player2Score.getGames());
        assertEquals(Points.LOVE, player1Score.getPoints());
        assertEquals(Points.LOVE, player2Score.getPoints());
    }

    @Test
    void deuceAgainstNotDeuce_thenThrowException(){
        player1Score.setPoints(Points.DEUCE);
        player2Score.setPoints(Points.ADVANTAGE);

        assertThrows(InvalidStageStateException.class, () -> scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));

        player1Score.setPoints(Points.FORTY);
        player2Score.setPoints(Points.DEUCE);

        assertThrows(InvalidStageStateException.class, () -> scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));
    }

    @Test
    void advantageAgainstNotForty_thenThrowException(){
        player1Score.setPoints(Points.ADVANTAGE);
        player2Score.setPoints(Points.THIRTY);

        assertThrows(InvalidStageStateException.class, () -> scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));

        player1Score.setPoints(Points.LOVE);
        player2Score.setPoints(Points.ADVANTAGE);

        assertThrows(InvalidStageStateException.class, () -> scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));
    }

    @Test
    void player1WinsGameAt5Against4Games_resultsPlayer1WinsSet(){
        player1Score.setPoints(Points.FORTY);
        player1Score.setGames(5);
        player2Score.setPoints(Points.THIRTY);
        player2Score.setGames(4);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getSets() + 1, player1Score.getSets());
        assertEquals(0, player1Score.getGames());
        assertEquals(0, player2Score.getGames());
        assertEquals(Points.LOVE, player1Score.getPoints());
        assertEquals(Points.LOVE, player2Score.getPoints());
    }

    @Test
    void player1WinsGameAt6Against5Games_resultsPlayer1WinsSet(){
        player1Score.setPoints(Points.FORTY);
        player1Score.setGames(6);
        player2Score.setPoints(Points.THIRTY);
        player2Score.setGames(5);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getSets() + 1, player1Score.getSets());
        assertEquals(0, player1Score.getGames());
        assertEquals(0, player2Score.getGames());
        assertEquals(Points.LOVE, player1Score.getPoints());
        assertEquals(Points.LOVE, player2Score.getPoints());
    }

    @Test
    void player1WinsGameAt5Against5Games_thenSetIsContinuing(){
        player1Score.setPoints(Points.FORTY);
        player1Score.setGames(5);
        player2Score.setPoints(Points.THIRTY);
        player2Score.setGames(5);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);
        PlayerScore initialPlayer2Score = getPlayerScoreCopy(player2Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getSets(), player1Score.getSets());
        assertEquals(initialPlayer1Score.getGames() + 1, player1Score.getGames());
        assertEquals(initialPlayer2Score.getGames(), player2Score.getGames());
        assertEquals(Points.LOVE, player1Score.getPoints());
        assertEquals(Points.LOVE, player2Score.getPoints());
    }

    @Test
    void gamesScore6_6_resultsTieBreakMatchState(){
        ongoingMatch.setMatchState(MatchState.ONGOING);
        player1Score.setGames(5);
        player1Score.setPoints(Points.FORTY);
        player2Score.setGames(6);
        player2Score.setPoints(Points.LOVE);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(MatchState.TIE_BREAK, ongoingMatch.getMatchState());
    }

    @Test
    void player1ScoresAt6Against6WhileTieBreak_resultsSetIsContinuing(){
        ongoingMatch.setMatchState(MatchState.TIE_BREAK);
        player1Score.setGames(6);
        player1Score.setTieBreakPoints(6);
        player2Score.setGames(6);
        player2Score.setTieBreakPoints(6);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);
        PlayerScore initialPlayer2Score = getPlayerScoreCopy(player2Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getSets(), player1Score.getSets());
        assertEquals(initialPlayer1Score.getTieBreakPoints() + 1, player1Score.getTieBreakPoints());
        assertEquals(initialPlayer2Score.getTieBreakPoints(), player2Score.getTieBreakPoints());
        assertEquals(initialPlayer1Score.getGames(), player1Score.getGames());
        assertEquals(initialPlayer2Score.getGames(), player2Score.getGames());
        assertEquals(MatchState.TIE_BREAK, ongoingMatch.getMatchState());
    }

    @Test
    void player1ScoresAt35Against35WhileTieBreak_resultsSetIsContinuing(){
        ongoingMatch.setMatchState(MatchState.TIE_BREAK);
        player1Score.setGames(6);
        player1Score.setTieBreakPoints(35);
        player2Score.setGames(6);
        player2Score.setTieBreakPoints(35);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);
        PlayerScore initialPlayer2Score = getPlayerScoreCopy(player2Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getSets(), player1Score.getSets());
        assertEquals(initialPlayer1Score.getTieBreakPoints() + 1, player1Score.getTieBreakPoints());
        assertEquals(initialPlayer2Score.getTieBreakPoints(), player2Score.getTieBreakPoints());
        assertEquals(initialPlayer1Score.getGames(), player1Score.getGames());
        assertEquals(initialPlayer2Score.getGames(), player2Score.getGames());
        assertEquals(MatchState.TIE_BREAK, ongoingMatch.getMatchState());
    }

    @Test
    void player1ScoresAt6Against5WhileTieBreak_resultsPlayer1WinsSet(){
        ongoingMatch.setMatchState(MatchState.TIE_BREAK);
        player1Score.setGames(6);
        player1Score.setTieBreakPoints(6);
        player2Score.setGames(6);
        player2Score.setTieBreakPoints(5);

        PlayerScore initialPlayer1Score = getPlayerScoreCopy(player1Score);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(initialPlayer1Score.getSets() + 1, player1Score.getSets());
        assertEquals(0, player1Score.getTieBreakPoints());
        assertEquals(0, player2Score.getTieBreakPoints());
        assertEquals(0, player1Score.getGames());
        assertEquals(0, player2Score.getGames());
        assertEquals(MatchState.ONGOING, ongoingMatch.getMatchState());
    }

    @Test
    void player1WinsSetAt1Set_resultsPlayer1WinsMatch() {
        player1Score.setPoints(Points.FORTY);
        player1Score.setGames(6);
        player1Score.setSets(1);
        player2Score.setPoints(Points.THIRTY);
        player2Score.setGames(5);

        scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId());

        assertEquals(2, player1Score.getSets());
        assertEquals(MatchState.FINISHED, ongoingMatch.getMatchState());
    }

    private PlayerScore getPlayerScoreCopy(PlayerScore playerScore){
        return playerScore.toBuilder().build();
    }

}
