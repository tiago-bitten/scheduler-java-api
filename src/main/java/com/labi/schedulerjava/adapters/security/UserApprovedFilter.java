package com.labi.schedulerjava.adapters.security;

import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserApprovedFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = jwtTokenProvider.getUserFromToken(request.getHeader("Authorization"));
        if (!user.getIsApproved())
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Sua conta ainda não foi aprovada");
        else
            filterChain.doFilter(request, response);
    }

    private boolean isPublicRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/v1/auth");
    }
}
