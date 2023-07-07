package com.quid.innteci.manage.task.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class implements a CORS (Cross-Origin Resource Sharing) filter
 * to handle Cross-Origin requests in a Spring Web application.
 */
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if any.
    }

    /**
     * Filters the incoming HTTP request and adds necessary CORS headers
     * to enable Cross-Origin requests.
     *
     * @param servletRequest  the incoming ServletRequest
     * @param servletResponse the outgoing ServletResponse
     * @param filterChain     the filter chain for invoking subsequent filters
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if any Servlet-related error occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "180");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // Cleanup code, if any.
    }
}