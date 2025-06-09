package com.nataliya.filter;

import com.nataliya.exception.CustomException;
import com.nataliya.util.JspUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter("/*")
@Slf4j
public class ErrorHandlingFilter implements Filter {

    private static final String ERROR_JSP_NAME = "error";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (CustomException e) {
            logException(e);
            sendErrorResponse(e, req, resp);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            req.setAttribute("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("message", "Something is wrong: " + e.getMessage());
            resp.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher(JspUtil.getPath(ERROR_JSP_NAME)).forward(req, resp);
        }
    }

    private void sendErrorResponse(CustomException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("status", e.getStatusCode());
        req.setAttribute("message", e.getMessage());
        resp.setStatus(e.getStatusCode());
        req.getRequestDispatcher(JspUtil.getPath(ERROR_JSP_NAME)).forward(req, resp);
    }

    private void logException(CustomException e){
        if (e.getStatusCode() == HttpServletResponse.SC_BAD_REQUEST || e.getStatusCode() == HttpServletResponse.SC_NOT_FOUND){
            log.info(e.getMessage(), e);
        } else {
            log.warn(e.getMessage(), e);
        }
    }
}
