package com.quid.innteci.manage.task.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class implements a JWT token filter to authenticate requests.
 * It extends the GenericFilterBean class provided by Spring Security.
 */
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructs an instance of JwtTokenFilter.
     *
     * @param jwtTokenProvider the JwtTokenProvider instance for JWT token management
     */
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Filters the request and performs JWT token authentication.
     *
     * @param req          the ServletRequest object
     * @param res          the ServletResponse object
     * @param filterChain  the FilterChain object for invoking the next filter in the chain
     * @throws IOException      if an I/O error occurs during filtering
     * @throws ServletException if a servlet exception occurs during filtering
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth;
            try {
                auth = jwtTokenProvider.getAuthentication(token);
            } catch (UsernameNotFoundException e) {
                // If the user no longer exists in the system, clear the security context
                SecurityContextHolder.clearContext();
                throw e;
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }
}