package com.nataliya.service.scoremanager;

import com.nataliya.model.MatchState;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.PlayerScore;

public class MatchStageManager extends ScoreStageManager{

    public MatchStageManager(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch) {
        super(pointWinnerScore, pointLoserScore, ongoingMatch);
    }

    @Override
    public boolean isStageComplete() {
        return pointWinnerScore.getSets() == 2;
    }

    @Override
    public void handleStage() {
        ongoingMatch.setMatchState(MatchState.FINISHED);
    }
}
