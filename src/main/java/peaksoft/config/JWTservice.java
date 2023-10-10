package peaksoft.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import peaksoft.entity.User;

import java.time.ZonedDateTime;
import java.util.Date;


@Service
public class JWTservice {
    @Value("${app.jwt.secret}")
    private String secretKey;


    //create token
    public String  createToken(User user){
        Algorithm algorithm=Algorithm.HMAC512(secretKey);
        String token = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(1).toInstant()))
                .withClaim("id", user.getId())
                .withClaim("fullName", user.getFullName())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
        return token;
    }

    //verify jwt
    public User verifyJwt(String token){
        Algorithm algorithm=Algorithm.HMAC512(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWTconvertToUser(decodedJWT);
    }

    private User decodedJWTconvertToUser(DecodedJWT decodedJWT) {
        User user = new User();
        user.setId(decodedJWT.getClaim("id").asLong());
        user.setFullName(decodedJWT.getClaim("fullName").asString());
        user.setEmail(decodedJWT.getClaim("email").asString());
        user.setRole(Role.valueOf(decodedJWT.getClaim("role").asString()));
        return user;
    }
}
