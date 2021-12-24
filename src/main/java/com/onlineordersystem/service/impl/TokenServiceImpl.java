package com.onlineordersystem.service.impl;

import com.onlineordersystem.domain.Token;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.repository.TokenRepository;
import com.onlineordersystem.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${oos.auth.jwtSecret}")
    private String jwtSecret;

    @Value("${oos.auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void storeToken(String token) {
        Token newToken = new Token();
        newToken.setToken(token);
        String subject = getUserNameFromJwtToken(token);
        newToken.setSubject(subject);
        newToken.setExpiresAt(getExpirationFromJwtToken(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        tokenRepository.save(newToken);
        log.warn("Token of {} has successfully been granted.", subject);
    }

    @Override
    public void removeToken(String subject) {
        log.warn("Token of {} has successfully been revoked", subject);
        tokenRepository.deleteById(subject);
    }

    @Override
    public String generateJwtToken(Authentication authentication) {

        PrincipleDTO userPrincipal = (PrincipleDTO) authentication.getPrincipal();

        Date now = new Date();
        return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    @Override
    public String getUserNameFromJwtToken(String token) {
        return parseToken(token).getSubject();
    }

    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    private Date getExpirationFromJwtToken(String token) {
        return parseToken(token).getExpiration();
    }

    @Override
    public boolean validateJwtToken(String authToken) {
        try {
            if (!tokenRepository.existsById(getUserNameFromJwtToken(authToken))) {
                return false;
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
