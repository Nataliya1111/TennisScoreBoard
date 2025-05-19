package com.nataliya.util;

import com.nataliya.dto.PlayerScoreDto;
import com.nataliya.dto.ScoreDto;
import com.nataliya.exception.InvalidStageStateException;
import com.nataliya.model.PlayerScore;
import com.nataliya.model.Points;
import com.nataliya.model.Score;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class MappingUtil {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    static{
        TypeMap<PlayerScore, PlayerScoreDto> typeMap = MODEL_MAPPER.createTypeMap(PlayerScore.class, PlayerScoreDto.class);
        typeMap.setConverter(ctx -> {
            PlayerScore source = ctx.getSource();
            PlayerScoreDto playerScoreDto = new PlayerScoreDto();
            playerScoreDto.setSets(source.getSets());
            playerScoreDto.setGames(source.getGames());
            if (isScoreNotValid(source)){
                throw new InvalidStageStateException(String.format("Invalid PlayerScore: points - %s, tie break points - %d", source.getPoints().getLabel(), source.getTieBreakPoints()));
            }
            playerScoreDto.setPoints(source.getTieBreakPoints() > 0
                    ? String.valueOf(source.getTieBreakPoints()) : source.getPoints().getLabel());
            return playerScoreDto;
        });

    }

    private MappingUtil(){
    }

    public static ScoreDto convertToDto(Score score){
        PlayerScoreDto player1Score = MODEL_MAPPER.map(score.getPlayer1Score(), PlayerScoreDto.class);
        PlayerScoreDto player2Score = MODEL_MAPPER.map(score.getPlayer2Score(), PlayerScoreDto.class);
        return new ScoreDto(player1Score, player2Score);
    }

    private static boolean isScoreNotValid(PlayerScore score){
        return  score.getPoints() == Points.GAME || (score.getPoints() != Points.LOVE && score.getTieBreakPoints() > 0);
    }


}
