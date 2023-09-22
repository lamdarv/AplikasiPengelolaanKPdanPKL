package com.jtk.ps.api.security;

import com.jtk.ps.api.dto.RefreshResponse;
import com.jtk.ps.api.dto.VerifyResponse;
import com.jtk.ps.api.model.Account;
import com.jtk.ps.api.service.AccountService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.CookieUtil;
import com.jtk.ps.api.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;


@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${jwt.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private CookieUtil cookieUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT Token is in the form "Bearer token". Remove Bearer word and
        // get  only the Token
        if (!request.getServletPath().equals("/account/login") || !request.getServletPath().equals("/account/verify")) {
            String jwtToken = getJwtToken(request, true);
            try {
                boolean isValid = StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken);
                if (isValid) {
                    String username = jwtUtils.getUsernameFromToken(jwtToken);
                    UserDetails userDetails = accountService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            } catch (ExpiredJwtException  ex) {
                try {
                    String refreshToken = getRefreshToken(request);
                    boolean isValid = StringUtils.hasText(refreshToken) && jwtTokenUtil.validateToken(refreshToken);
                    if (isValid) {
                        allowForRefreshToken(ex, request, response);
                    }else request.setAttribute("exception", ex);
                }catch (Exception e) {
                    request.setAttribute("exception", e);
                }
            }catch (BadCredentialsException ex) {
                request.setAttribute("exception", ex);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request, HttpServletResponse response){
        RefreshResponse refreshResponse = accountService.refresh(getRefreshToken(request));
        Map<String, Object> r = refreshResponse.getResponse();
        String sub = (String) r.get("sub");
        Account account = accountService.findAccountById(Integer.parseInt(sub));
        if (account != null) {
            UserDetails userDetails = accountService.loadUserByUsername(account.getUsername());
            refreshResponse.getHeaders().forEach((key, value) -> {
                if (value.get(0) != null) {
                    response.setHeader(key, value.get(0));
                    HttpCookie.parse(value.get(0)).forEach(cookie -> {
                        request.setAttribute("accessToken", cookie.getValue());
                        response.addCookie(new Cookie(cookie.getName(), cookie.getValue()));
                    });
                }
            });
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (accessTokenCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (refreshTokenCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getJwtToken(HttpServletRequest request, boolean fromCookie) {
        if (fromCookie) return getJwtFromCookie(request);

        return getJwtFromRequest(request);
    }
}
