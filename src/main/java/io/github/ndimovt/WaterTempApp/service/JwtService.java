package io.github.ndimovt.WaterTempApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value(value = "{spring.security.jwt.secret-key}")
    private String secretKey;
    @Value(value = "{spring.security.jwt.expiration-time}")
    private long jwtExpiration;
    public long getJwtExpiration() {
        return jwtExpiration;
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return buildToken(claims, userDetails, jwtExpiration);
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = userDetails.getUsername();
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token){
        return expirationDate(token).before(new Date());
    }
    private Date expirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private String buildToken(Map<String, Object> extractClaims, UserDetails userDetails, long expiration){
        return Jwts.
                builder().
                claims(extractClaims).
                subject(userDetails.getUsername()).
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + expiration)).
                signWith(getSignInKey()).
                compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts.
                parser().
                verifyWith((SecretKey) getSignInKey()).
                build().
                parseSignedClaims(token).
                getPayload();
    }
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
