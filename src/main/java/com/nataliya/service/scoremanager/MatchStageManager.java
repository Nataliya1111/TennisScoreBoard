package com.nataliya.service.scoremanager;

import com.nataliya.model.MatchState;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.PlayerScore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("Ongoing match with UUID {} has been finished", ongoingMatch.getUuid());
    }
}
