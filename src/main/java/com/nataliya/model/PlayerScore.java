package com.nataliya.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PlayerScore {

    private int sets = 0;
    private int games = 0;
    private Points points = Points.LOVE;
    private int tieBreakPoints = 0;

    public void incrementSets() {
        this.sets += 1;
    }

    public void incrementGames() {
        this.games += 1;
    }

    public void incrementTieBreakPoints() {
        this.tieBreakPoints += 1;
    }

    public void setPointsToZero() {
        points = Points.LOVE;
    }

    public void setTieBreakPointsToZero() {
        tieBreakPoints = 0;
    }

    public void setGamesToZero() {
        games = 0;
    }
}
