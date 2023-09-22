package com.jtk.ps.api.security;

import com.jtk.ps.api.dto.PayloadJwt;
import com.jtk.ps.api.dto.VerifyResponse;
import com.jtk.ps.api.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${jwt.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    @Autowired
    private RestTemplate restTemplates;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", accessTokenCookieName + " = " + getJwtAccessTokenFromCookie(request)+"; "+refreshTokenCookieName+" = "+getJwtRefreshTokenFromCookie(request));
            HttpEntity<String> req = new HttpEntity<>(headers);
            ResponseEntity<VerifyResponse> verifyResponse = restTemplates.exchange("http://account-service/account/verify", HttpMethod.POST, req, VerifyResponse.class);
            HttpStatus statusCode = verifyResponse.getStatusCode();

            if(statusCode.is3xxRedirection()){
                response.reset();
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                return;
            }
             else if (statusCode.is4xxClientError()) {
                response.reset();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            } else if (statusCode.is5xxServerError()) {
                response.reset();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            if (verifyResponse.getBody() != null && Objects.requireNonNull(verifyResponse.getBody()).getData() != null) {
                PayloadJwt payloadJwt = Objects.requireNonNull(verifyResponse.getBody()).getData();

                request.setAttribute(Constant.VerifyConstant.STATUS, HttpStatus.OK.value());
                request.setAttribute(Constant.VerifyConstant.ID_PRODI, payloadJwt.getIdProdi());
                request.setAttribute(Constant.VerifyConstant.SUB, payloadJwt.getSub());
                request.setAttribute(Constant.VerifyConstant.ID_ROLE, payloadJwt.getIdRole());
                request.setAttribute(Constant.VerifyConstant.ID, payloadJwt.getId());
                request.setAttribute(Constant.VerifyConstant.NAME, payloadJwt.getName());

                UserDetails userDetails = new User(payloadJwt.getSub(), "", getAuthorities(payloadJwt.getIdRole()));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                request.setAttribute(Constant.VerifyConstant.STATUS, 400);
            }
        } catch (Exception e) {
            request.setAttribute(Constant.VerifyConstant.STATUS, 500);
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(accessTokenCookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getJwtRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(refreshTokenCookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer idRoles) {
        switch (idRoles) {
            case 0:
                return Collections.singleton(new SimpleGrantedAuthority(Constant.Role.COMMITTEE));
            case 1:
                return Collections.singleton(new SimpleGrantedAuthority(Constant.Role.PARTICIPANT));
            case 2:
                return Collections.singleton(new SimpleGrantedAuthority(Constant.Role.COMPANY));
            case 3:
                return Collections.singleton(new SimpleGrantedAuthority(Constant.Role.HEAD_STUDY_PROGRAM));
            default:
                return Collections.emptyList();
        }
    }


}
