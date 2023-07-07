package com.quid.innteci.manage.task.security.jwt;

import com.quid.innteci.manage.task.model.User;
import com.quid.innteci.manage.task.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

/**
 * This component provides JWT token generation, token validation, and authentication.
 */
@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long validityInMilliseconds;
    private final UserRepository userRepository;

    /**
     * Constructs an instance of JwtTokenProvider.
     *
     * @param userRepository the UserRepository instance for user retrieval
     */
    public JwtTokenProvider(UserRepository userRepository) {
        this.secretKey = Base64.getEncoder().encodeToString("secret".getBytes());
        this.validityInMilliseconds = 3600000; // 1h
        this.userRepository = userRepository;
    }

    /**
     * Creates a JWT token for the given username.
     *
     * @param username the username (email) of the user
     * @return the generated JWT token
     */
    public String createToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: "));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Resolves the JWT token from the HTTP request.
     *
     * @param req the HttpServletRequest object
     * @return the resolved JWT token
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    /**
     * Retrieves the authentication details from the JWT token.
     *
     * @param token the JWT token
     * @return the Authentication object representing the user's authentication
     */
    public Authentication getAuthentication(String token) {
        User user = userRepository.findByEmail(getEmail(token))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: "));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) // validating the email as the username
                .password(user.getPassword())
                .authorities(new ArrayList<>()) // you can set the required authorities/roles here
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Retrieves the email from the JWT token.
     *
     * @param token the JWT token
     * @return the email extracted from the token
     */
    public String getEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("email");
    }

    /**
     * Retrieves the user ID from the JWT token.
     *
     * @param token the JWT token
     * @return the user ID extracted from the token
     */
    public Long getIdUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf((Integer) claims.get("userId"));
    }

    /**
     * Custom exception to be thrown when there's an error with JWT authentication.
     */
    public static class JwtAuthenticationException extends RuntimeException {
        public JwtAuthenticationException(String msg) {
            super(msg);
        }
    }
}
