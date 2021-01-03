package com.sleepseek.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.Lists;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import static java.util.Objects.isNull;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final SecurityConstants securityConstants;

    public JWTAuthorizationFilter(AuthenticationManager authManager, SecurityConstants securityConstants) {
        super(authManager);
        this.securityConstants = securityConstants;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(securityConstants.getTokenPrefix());

        if (header == null || header.startsWith(securityConstants.getTokenPrefix())) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(securityConstants.getHeaderString());
        if (isNull(token)) {
            return null;
        }
        String user = JWT.require(Algorithm.HMAC512(securityConstants.getKey().getBytes()))
                .build()
                .verify(token.replace(securityConstants.getTokenPrefix(), ""))
                .getSubject();

        return Optional.of(user)
                .map(u -> new UsernamePasswordAuthenticationToken(u, null, Lists.newArrayList()))
                .orElse(null);
    }
}