package com.avanes.backend.config;

import com.avanes.backend.repository.UserRepository;
import com.avanes.backend.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.avanes.backend.models.User;
import com.avanes.backend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        User user = userRepository.findByUsername(username);
        tokenMap.put("userId",  user.getUserId().toString());
        tokenMap.put("username", user.getUsername());
        tokenMap.put("role", user.getRole());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), tokenMap);
    }
}