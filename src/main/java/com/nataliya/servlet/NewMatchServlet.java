package com.nataliya.servlet;

import com.nataliya.dto.NewMatchDto;
import com.nataliya.exception.InvalidRequestException;
import com.nataliya.model.OngoingMatch;
import com.nataliya.service.OngoingMatchService;
import com.nataliya.util.JspUtil;
import com.nataliya.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private static final String NEW_MATCH_JSP_NAME = "new-match";
    private static final String REDIRECT_NAME = "match-score?uuid=%s";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspUtil.getPath(NEW_MATCH_JSP_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String player1Name = req.getParameter("player1Name");
        String player2Name = req.getParameter("player2Name");
        req.setAttribute("player1Name", player1Name);
        req.setAttribute("player2Name", player2Name);

        NewMatchDto newMatchDto = new NewMatchDto(player1Name, player2Name);

        try {
            ValidationUtil.validate(newMatchDto);
        } catch (InvalidRequestException e) {
            req.setAttribute("Error_message", e.getMessage());
            req.getRequestDispatcher(JspUtil.getPath(NEW_MATCH_JSP_NAME)).forward(req, resp);
            return;
        }

        final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
        OngoingMatch ongoingMatch = ongoingMatchService.createOngoingMatch(newMatchDto);

        String redirectLocation = String.format(REDIRECT_NAME, URLEncoder
                .encode(ongoingMatch.getUuid().toString(), StandardCharsets.UTF_8));

        resp.sendRedirect(redirectLocation);
    }
}
