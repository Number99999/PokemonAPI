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
    private final String jwt_secret = "joker";
    private final Long jwt_exprition = 604800000L;
    private final String header = "pokemon";
    public String createJWT(Pokemon pokemon)
    {
        String jwt = Jwts.builder()
                .setSubject(pokemon.toString())
                .signWith(SignatureAlgorithm.HS512, jwt_secret)
                .compact();
        return jwt;
    }
}
