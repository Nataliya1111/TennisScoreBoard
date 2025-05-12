package com.nataliya.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Score {

    private final PlayerScore player1Score = new PlayerScore();
    private final PlayerScore player2Score = new PlayerScore();
}
