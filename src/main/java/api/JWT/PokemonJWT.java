package api.JWT;

import api.Model.Pokemon;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class PokemonJWT {
    private static final String jwt_secret = "duong2k1";
    private static final Long jwt_exprition = 604800000L;
    private final String header = "pokemon";
    public static String createJWT(Pokemon pokemon)
    {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+jwt_exprition);
        String jwt = Jwts.builder()
                .setSubject(pokemon.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwt_secret)
                .compact();
        return jwt;
    }
}
