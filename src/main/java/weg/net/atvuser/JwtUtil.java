package weg.net.atvuser;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtil {

    //criar metodo de gerar senha
    public String gerarToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuer("WEG") // é a "WEG" que gera esse token, exemplo de entidade no caso
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
                .signWith(SignatureAlgorithm.NONE, "senha123") // tipo de algoritimo que vai ser utilizado para avaliar se o token que ele está recebendo é o certo
                .subject(userDetails.getUsername()) // esse sempre deve ser único, pois a partir dele iremos procurar o usuário
                .compact();
    }

    private Jws<Claims> validarToken(String token) {
        return  getParser().parseSignedClaims(token);
    }

    private JwtParser getParser() {
        return Jwts.parser().setSigningKey("senha123")
                .build();
    }

    public String getUsername(String token) {
        return validarToken(token)
                .getPayload()
                .getSubject();
    }
}
