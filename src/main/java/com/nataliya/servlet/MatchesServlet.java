package com.nataliya.servlet;

import com.nataliya.model.entity.Match;
import com.nataliya.service.PersistentMatchService;
import com.nataliya.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private static final String MATCHES_JSP_NAME = "matches";
    private final PersistentMatchService persistentMatchService = PersistentMatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String page = req.getParameter("page");
        String filterByPlayerName = req.getParameter("filter_by_player_name");

        List<Match> matches = persistentMatchService.getPersistentMatches();
        req.setAttribute("matches", matches);
        req.getRequestDispatcher(JspUtil.getPath(MATCHES_JSP_NAME)).forward(req, resp);
    }
}
