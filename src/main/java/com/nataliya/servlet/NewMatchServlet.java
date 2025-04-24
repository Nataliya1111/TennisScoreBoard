package com.nataliya.servlet;

import com.nataliya.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private static final String JSP_NAME = "new-match";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspUtil.getPath(JSP_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String player1Name = req.getParameter("player1Name");
        String player2Name = req.getParameter("player2Name");




    }
}
