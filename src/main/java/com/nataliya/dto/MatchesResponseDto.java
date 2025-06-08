package com.nataliya.dto;

import com.nataliya.model.entity.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MatchesResponseDto {

    private List<Match> matches;
    private String notFoundMessage;
    private List<Integer> pagesToShow;
    private int currentPage;
    private int lastPage;

}
