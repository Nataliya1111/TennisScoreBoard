package com.nataliya.service.scoremanager;

import com.nataliya.model.OngoingMatch;
import com.nataliya.model.PlayerScore;

public abstract class ScoreStageManager {
    protected PlayerScore pointWinnerScore;
    protected PlayerScore pointLoserScore;
    protected OngoingMatch ongoingMatch;

    public ScoreStageManager(PlayerScore pointWinnerScore, PlayerScore pointLoserScore, OngoingMatch ongoingMatch) {
        this.pointWinnerScore = pointWinnerScore;
        this.pointLoserScore = pointLoserScore;
        this.ongoingMatch = ongoingMatch;
    }

    public abstract boolean isStageComplete();

    public abstract void handleStage();
}
