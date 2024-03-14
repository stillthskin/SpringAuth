package com.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY = "347270493c343c734c5d40523d7535702e44662f632855276132313170";
    public String getUsername( String jwt){

        return returnClaim(jwt,Claims::getSubject);
    }

    public <T> T returnClaim(String token, Function<Claims,T> claimsResolver){

        final  Claims  claims = returnClaims(token);
        return claimsResolver.apply(claims);

    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +1000*60+24))
                .signWith(getPublicSigningKey(),Jwts.SIG.HS256)
                .compact();
    }
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return returnClaim(token, Claims::getExpiration);
    }
    private Claims returnClaims(String jwt){
        return Jwts.parser().verifyWith(getPublicSigningKey()).build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private SecretKey getPublicSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
