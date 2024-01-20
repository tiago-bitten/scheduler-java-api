package com.labi.schedulerjava.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labi.schedulerjava.core.domain.exception.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAccessDenied implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                accessDeniedException.getMessage(),
                HttpServletResponse.SC_FORBIDDEN
        );

        objectMapper.writeValue(response.getWriter(), errorMessage);
    }
}
