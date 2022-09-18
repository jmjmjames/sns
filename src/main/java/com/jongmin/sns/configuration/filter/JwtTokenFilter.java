package com.jongmin.sns.configuration.filter;

import com.jongmin.sns.dto.security.UserPrincipal;
import com.jongmin.sns.service.UserService;
import com.jongmin.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(("Bearer "))) {
            log.error("Error occurs while getting header. Header is null or invalid {}", request.getRequestURL());
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            if (JwtTokenUtils.isTokenExpired(token, key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            }

            String userName = JwtTokenUtils.getUsername(token, key);
            UserPrincipal userPrincipal = userService.loadUserByUserName(userName).toPrincipal();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, userPrincipal.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (RuntimeException e) {
            log.error("Error occurs while validation. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
