package com.nataliya.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataliya.dto.ScoreDto;
import com.nataliya.model.MatchState;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.Score;
import com.nataliya.model.entity.Match;
import com.nataliya.service.PersistentMatchService;
import com.nataliya.service.OngoingMatchService;
import com.nataliya.service.ScoreCountService;
import com.nataliya.util.JspUtil;
import com.nataliya.util.ScoreMapper;
import com.nataliya.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private static final String MATCH_SCORE_JSP_NAME = "match-score";
    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
    private final PersistentMatchService persistentMatchService = PersistentMatchService.getInstance();
    private final ScoreCountService scoreCountService = new ScoreCountService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = ValidationUtil.getValidUuid(req.getParameter("uuid"));

        OngoingMatch ongoingMatch = ongoingMatchService.getOngoingMatch(uuid);
        req.setAttribute("match", ongoingMatch);

        req.getRequestDispatcher(JspUtil.getPath(MATCH_SCORE_JSP_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        UUID uuid = ValidationUtil.getValidUuid(req.getParameter("uuid"));
        Long pointWinnerId = ValidationUtil.getValidPointWinnerId(req.getParameter("player_id"));

        OngoingMatch ongoingMatch = ongoingMatchService.getOngoingMatch(uuid);

        Score newScore = scoreCountService.updateScore(ongoingMatch, pointWinnerId);
        ScoreDto scoreDto = ScoreMapper.convertToDto(newScore, ongoingMatch.getMatchState());

        if (ongoingMatch.getMatchState() == MatchState.FINISHED) {
            Match finishedMatch = Match.builder().player1(ongoingMatch.getPlayer1())
                    .player2(ongoingMatch.getPlayer2()).winner(ongoingMatch.getLastPointWinner()).build();
            persistentMatchService.saveFinishedMatch(finishedMatch);
            ongoingMatchService.deleteOngoingMatch(uuid);
        }

        objectMapper.writeValue(resp.getWriter(), scoreDto);
    }
}
