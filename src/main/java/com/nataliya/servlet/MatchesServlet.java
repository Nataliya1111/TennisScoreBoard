package com.nataliya.servlet;

import com.nataliya.dto.MatchPageResult;
import com.nataliya.dto.MatchesResponseDto;
import com.nataliya.model.entity.Match;
import com.nataliya.service.MatchPaginationService;
import com.nataliya.util.JspUtil;
import com.nataliya.util.ValidationUtil;
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
    private final MatchPaginationService matchPaginationService = MatchPaginationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("filter_by_player_name");
        int pageNumber = ValidationUtil.getValidPageNumber(req.getParameter("page"));

        MatchPageResult matchPageResult;
        String notFoundMessage = null;

        if (name == null || name.isBlank()) {
            matchPageResult = matchPaginationService.getMatchesPageAndLastPageNumber(pageNumber);
            if (matchPageResult.matchesPage().isEmpty()) {
                notFoundMessage = "No matches found";
            }
        } else {
            name = name.trim();
            matchPageResult = matchPaginationService.getMatchesPageAndLastPageNumberByName(name, pageNumber);
            if (matchPageResult.matchesPage().isEmpty()) {
                notFoundMessage = String.format("Matches with player %s not found", name);
            }
        }

        List<Match> matches = matchPageResult.matchesPage();
        int lastPageNumber = matchPageResult.lastPageNumber();
        List<Integer> pagesToShow = matchPaginationService.getPagesToShow(lastPageNumber, pageNumber);

        MatchesResponseDto matchesResponseDto = new MatchesResponseDto(matches, notFoundMessage,
                pagesToShow, pageNumber, lastPageNumber);

        req.setAttribute("matches_response_dto", matchesResponseDto);
        req.getRequestDispatcher(JspUtil.getPath(MATCHES_JSP_NAME)).forward(req, resp);
    }
}
