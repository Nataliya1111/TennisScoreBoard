package com.nataliya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto {

    private PlayerScoreDto player1Score;
    private PlayerScoreDto player2Score;
}
