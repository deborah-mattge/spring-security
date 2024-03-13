package weg.net.atvuser;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private final SecretKey key;
    public JwtUtil(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = encoder.encode("senha123");
        this.key = Keys.hmacShaKeyFor(senha.getBytes());
    }

    //criar metodo de gerar senha
    public String gerarToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuer("WEG") // é a "WEG" que gera esse token, exemplo de entidade no caso
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
                .signWith(this.key, Jwts.SIG.HS256) // tipo de algoritimo que vai ser utilizado para avaliar se o token que ele está recebendo é o certo
                .subject(userDetails.getUsername()) // esse sempre deve ser único, pois a partir dele iremos procurar o usuário
                .compact();
    }

    private Jws<Claims> validarToken(String token) {
        return  getParser().parseSignedClaims(token);
    }

    private JwtParser getParser() {
        return Jwts.parser().verifyWith(this.key)
                .build();
    }

    public String getUsername(String token) {
        return validarToken(token)
                .getPayload()
                .getSubject();
    }
}
