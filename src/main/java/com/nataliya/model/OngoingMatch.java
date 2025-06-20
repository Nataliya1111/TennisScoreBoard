package com.nataliya.model;

import com.nataliya.model.entity.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class OngoingMatch {

    private final Player player1;
    private final Player player2;
    private final Score score;
    private final UUID uuid;
    private Player lastPointWinner;
    private MatchState matchState;

    public OngoingMatch(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.lastPointWinner = null;
        this.score = new Score();
        this.uuid = UUID.randomUUID();
        this.matchState = MatchState.ONGOING;
    }


}
