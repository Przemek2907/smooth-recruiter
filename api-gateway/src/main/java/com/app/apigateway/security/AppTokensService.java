package com.app.apigateway.security;

import com.app.apigateway.exception.AppSecurityException;
import com.app.apigateway.proxy.FindUserProxy;
import com.app.apigateway.security.dto.TokensDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class AppTokensService {

    @Value("${tokens.access-token.expiration-date-ms}")
    private Long accessTokenExpirationDateMs;

    @Value("${tokens.refresh-token.expiration-date-ms}")
    private Long refreshTokenExpirationDateMs;

    @Value("${tokens.refresh-token.property}")
    private String refreshTokenProperty;

    @Value("${tokens.bearer}")
    protected String tokenBearer;

    private final SecretKey secretKey;

    private final FindUserProxy findUserProxy;

    public AppTokensService(SecretKey secretKey, FindUserProxy findUserProxy) {
        this.secretKey = secretKey;
        this.findUserProxy = findUserProxy;
    }

    public TokensDto generateTokens(Authentication authentication) {

        var currentDateMs = System.currentTimeMillis();
        var currentDate = new Date(currentDateMs);

        var accessTokensDateMs = currentDateMs + accessTokenExpirationDateMs;
        var accessTokenDate = new Date(accessTokensDateMs);

        var refreshTokenDateMs = currentDateMs + refreshTokenExpirationDateMs;
        var refreshTokenDate = new Date(refreshTokenDateMs);

        var user = findUserProxy.getUser(authentication.getName());
        var userId = user.getData().getId();

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var refreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(refreshTokenDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public UsernamePasswordAuthenticationToken parse(String token) {
        if (token == null) {
            throw new AppSecurityException("Access token is null");
        }

        if (!token.startsWith(tokenBearer)) {
            throw new AppSecurityException("Access token has incorrect format");
        }

        var accessToken = token.replace(tokenBearer, "");

        if (hasTokenExpired(accessToken)) {
            throw new AppSecurityException("Your access token has expired");
        }

        var userId = id(accessToken);
        var user = findUserProxy.getUser(userId);
        return new UsernamePasswordAuthenticationToken(
                user.getData().getUsername(),
                null,
                List.of(new SimpleGrantedAuthority(user.getData().getRole().getFullName())));
    }

    private boolean hasTokenExpired(String token) {
        return new Date().after(expiration(token));
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date expiration(String token) {
        return claims(token).getExpiration();
    }

    private Long id(String token) {
        return Long.valueOf(claims(token).getSubject());
    }

}
