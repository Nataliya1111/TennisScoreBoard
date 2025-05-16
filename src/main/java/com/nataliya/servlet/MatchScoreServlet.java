package com.nataliya.servlet;

import com.nataliya.model.OngoingMatch;
import com.nataliya.service.OngoingMatchService;
import com.nataliya.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private static final String MATCH_SCORE_JSP_NAME = "match-score";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        //validate
//        if (uuid == null || uuid.isBlank()) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing UUID parameter");
//            return;
//        }

        OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
        OngoingMatch ongoingMatch = ongoingMatchService.getOngoingMatch(uuid);
        req.setAttribute("match", ongoingMatch);

        req.getRequestDispatcher(JspUtil.getPath(MATCH_SCORE_JSP_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //получить матч по UUID
        //получить параметром player1/player2
        //ScoreService.increaseScore(match.getScore, player1 - м.б. передавать dto: uuid,id)
    }
}
