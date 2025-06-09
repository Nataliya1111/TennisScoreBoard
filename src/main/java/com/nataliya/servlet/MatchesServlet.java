package com.nataliya.servlet;

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

        List<Match> matches;
        int lastPageNumber;
        String notFoundMessage = null;

        if (name == null || name.isBlank()){
            matches = matchPaginationService.getOneMatchesPage(pageNumber);
            lastPageNumber = matchPaginationService.getLastPageNumber();
            if (matches.isEmpty()){
                notFoundMessage =  "No matches found";
            }
        } else{
            name = name.trim();
            matches = matchPaginationService.getOneMatchesPageByName(name, pageNumber);
            lastPageNumber = matchPaginationService.getLastPageNumberByName(name);
            if (matches.isEmpty()){
                notFoundMessage = String.format("Matches with player %s not found", name);
            }
        }

        List<Integer> pagesToShow = matchPaginationService.getPagesToShow(lastPageNumber, pageNumber);

        MatchesResponseDto matchesResponseDto = new MatchesResponseDto(matches, notFoundMessage,
                pagesToShow, pageNumber, lastPageNumber);

        req.setAttribute("matches_response_dto", matchesResponseDto);
        req.getRequestDispatcher(JspUtil.getPath(MATCHES_JSP_NAME)).forward(req, resp);
    }
}
