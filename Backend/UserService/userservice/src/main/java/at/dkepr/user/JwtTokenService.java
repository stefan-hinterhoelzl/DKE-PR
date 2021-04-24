package at.dkepr.user;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import at.dkepr.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenService {

    private String secret;

    public JwtTokenService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(User user) {
        try{
            //Add some Claims
            Algorithm algo = Algorithm.HMAC256(this.secret);

            return JWT.create()
            .withIssuer("DefinitelyNotTwitter")
            .withSubject(user.getEmail())
            .withClaim("Firstname", user.getFirstname())
            .withClaim("Lastname", user.getLastname())
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(new Date(System.currentTimeMillis()+3600000))
            .sign(algo);
        } catch (JWTCreationException e) {
            e.printStackTrace();
            return "error";
        }
    }


    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenNotExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.after(new Date());
    }

    public Optional<Boolean> validateToken(String token) {
        return  isTokenNotExpired(token) ? Optional.of(Boolean.TRUE) : Optional.empty();
    }


}

