package ra.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ra.security.CustomUserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class TokenProvider {
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    @Value("${ra.jwt.expiration}")
    private long JWT_EXPIRATION;


//    public String generateToken(CustomUserDetails userDetails) {
//        Date now = new Date();
//        Date dateExpired = new Date(now.getTime() + this.JWT_EXPIRATION);
//
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(now)
//                .setExpiration(dateExpired)
//                .signWith(SignatureAlgorithm.HS256, this.JWT_SECRET)
//                .compact();
//    }


//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parser()
//                .setSigningKey(this.JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    public String generateToken(CustomUserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            CustomUserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, this.JWT_EXPIRATION);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            CustomUserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, this.JWT_SECRET)
                .compact();
    }

    public String decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims String is empty");
        }
        return false;
    }
}
