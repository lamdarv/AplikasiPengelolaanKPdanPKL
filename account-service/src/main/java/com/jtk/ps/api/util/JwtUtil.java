package com.jtk.ps.api.util;

import com.jtk.ps.api.dto.Token;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.repository.AccountRepository;
import com.jtk.ps.api.repository.LecturerRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String tokenSecret;

    @Value("${jwt.tokenExpirationMsec}")
    private Long tokenExpirationMsec;

    @Value("${jwt.refreshTokenExpirationMsec}")
    private Long refreshTokenExpirationMsec;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    public Token generateAccessToken(CustomUserDetails userDetails, Integer idProdi, Integer id) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority(ERole.COMMITTEE.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 0);
            Optional<Lecturer> dosen = lecturerRepository.findByAccountId(userDetails.getId());
            dosen.ifPresent(value -> claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id));
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.PARTICIPANT.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 1);
            claims.put(Constant.PayloadResponseConstant.ID_PRODI, idProdi);
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.COMPANY.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 2);
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.HEAD_STUDY_PROGRAM.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 3);
            Optional<Lecturer> dosen = lecturerRepository.findByAccountId(userDetails.getId());
            dosen.ifPresent(value -> claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id));
        }
        claims.put(Constant.PayloadResponseConstant.ID, id);
        return doGenerateToken(claims, String.valueOf(userDetails.getId()));
    }

    public Token generateAccessToken(CustomUserDetails userDetails, Integer idProdi, Integer id, String name) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority(ERole.COMMITTEE.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 0);
            Optional<Lecturer> dosen = lecturerRepository.findByAccountId(userDetails.getId());
            dosen.ifPresent(value -> claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id));
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.PARTICIPANT.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 1);
            claims.put(Constant.PayloadResponseConstant.ID_PRODI, idProdi);
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.COMPANY.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 2);
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.HEAD_STUDY_PROGRAM.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 3);
            Optional<Lecturer> dosen = lecturerRepository.findByAccountId(userDetails.getId());
            dosen.ifPresent(value -> claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id));
        }
        claims.put(Constant.PayloadResponseConstant.NAME, name);
        claims.put(Constant.PayloadResponseConstant.ID, id);
        return doGenerateToken(claims, String.valueOf(userDetails.getId()));
    }

    public Token generateAccessToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority(ERole.COMMITTEE.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 0);
            Optional<Lecturer> dosen = lecturerRepository.findByAccountId(userDetails.getId());
            dosen.ifPresent(value -> claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id));
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.PARTICIPANT.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 1);
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.COMPANY.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 2);
        }
        if (roles.contains(new SimpleGrantedAuthority(ERole.HEAD_STUDY_PROGRAM.name()))) {
            claims.put(Constant.PayloadResponseConstant.ID_ROLE, 3);
            Optional<Lecturer> dosen = lecturerRepository.findByAccountId(userDetails.getId());
            dosen.ifPresent(value -> claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id));
        }

        return doGenerateToken(claims, String.valueOf(userDetails.getId()));
    }

    private Token doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        long duration = now.getTime() + tokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now)
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, tokenSecret).compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public Token generateRefreshToken(String subject) {
        Date now = new Date();
        long duration = now.getTime() + refreshTokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        String id = claims.getSubject();

        Optional<Account> account = accountRepository.findById(Integer.parseInt(id));

        Account user = account.orElse(null);
        if(user != null){
            return user.getUsername();
        }

        return null;
    }

    public String getIdAccountFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public LocalDateTime getExpiryDateFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    public Boolean isExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String authToken) {
        if (authToken == null) return false;
        try {
            // Jwt token has not been tampered with
            Jws<Claims> claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        }
    }

    public HashMap<String, Object> getAllPayloadJwt(String token){
        if (token == null) return new HashMap<>();
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();

        return new HashMap<>(claims);
    }


    public ERole getRolesFromToken(String authToken) {
        ERole roles;
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken).getBody();
        Integer role = claims.get(Constant.PayloadResponseConstant.ID_ROLE, Integer.class);

        switch (role){
            case 0:
                roles = ERole.COMMITTEE;
                break;
            case 2:
                roles = ERole.COMPANY;
                break;
            case 3:
                roles = ERole.HEAD_STUDY_PROGRAM;
                break;
            default:
                roles = ERole.PARTICIPANT;
                break;
        }

        return roles;
    }

    public EProdi getProdiFromToken(String authToken){
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken).getBody();
        Integer prodi = claims.get(Constant.PayloadResponseConstant.ID_PRODI, Integer.class);

        return EProdi.valueOfId(prodi);
    }

    public Integer getRoleFromToken(String authToken){
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken).getBody();
        return claims.get(Constant.PayloadResponseConstant.ID_ROLE, Integer.class);
    }
}
