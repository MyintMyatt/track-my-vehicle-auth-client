package dev.orion.track_my_vehicle_auth_client;

import dev.orion.commons.client.auth.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthClientFilter extends OncePerRequestFilter {

    private final AuthClient authClient;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getServletPath().startsWith("/public")){
            var token = request.getHeader("Authorization");

            if(token != null && StringUtils.hasLength(token)){

            }
        }
        filterChain.doFilter(request, response);
    }
}
