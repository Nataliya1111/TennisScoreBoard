package com.nataliya.dto;

import com.nataliya.model.entity.Match;

import java.util.List;

public record MatchPageResult(List<Match> matchesPage, int lastPageNumber) {
}
