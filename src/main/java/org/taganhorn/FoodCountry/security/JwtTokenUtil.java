package org.taganhorn.FoodCountry.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    public List<String> getRoleFromToken(String token) {
        return getClaimFromToken(token, (it) -> {
            return it.get("role", List.class);
        });
    }

    public Integer getUserIdFromToken(String token) {
        return getClaimFromToken(token, (it) -> {
            return it.get("userId", Integer.class);
        });
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserEntity userEntity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userEntity.getRoles());
        claims.put("userId", userEntity.getId());
        return doGenerateToken(claims, userEntity.getEmail());
    }

    public String generateRefreshToken(String authToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authToken", authToken.substring(0,5)+authToken.substring(authToken.length()-6,authToken.length()-1));
        claims.put("createdDate", new Date().getTime());
        return Jwts.builder().setClaims(claims).setSubject("refresh_token").setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return !isTokenExpired(token);
    }
}
