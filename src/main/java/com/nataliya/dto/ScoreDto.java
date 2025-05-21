package com.nataliya.dto;

import com.nataliya.model.MatchState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto { //MatchScoreDto

    private PlayerScoreDto player1Score;
    private PlayerScoreDto player2Score;
    private MatchState matchState;
}
