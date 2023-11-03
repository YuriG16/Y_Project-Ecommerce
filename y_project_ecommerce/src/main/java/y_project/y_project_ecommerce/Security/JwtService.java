package y_project.y_project_ecommerce.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    private static final String SECRET_KEY = "357538782F4125442A472D4B6150645367566B59703373367639792442264528";

    public String extractEmailFromRequest (HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken = authHeader.substring(7);
        return extractEmailFromToken(jwtToken);
    }

    private String extractEmailFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims:: getSubject);
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken (UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken (Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims)
        .setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public Boolean isTokenValid ( String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return etractExpiration(token).before(new Date());
    }

    private Date etractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims (String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigninKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

}
