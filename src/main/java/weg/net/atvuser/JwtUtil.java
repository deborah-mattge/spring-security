package weg.net.atvuser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private final   Algorithm algorithm = Algorithm.HMAC256("senha123");


    //criar metodo de gerar senha
    public String gerarToken(UserDetails userDetails) {

        return JWT.create().withIssuer("WEG")// é a "WEG" que gera esse token, exemplo de entidade no caso
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
                // esse sempre deve ser único, pois a partir dele iremos procurar o usuário

    }

//    private Jws<Claims> validarToken(String token) {
//        return  getParser().parseSignedClaims(token);
//    }
//
//    private JwtParser getParser() {
//        return Jwts.parser().verifyWith(this.key)
//                .build();
//    }

    public String getUsername(String token) {
        return JWT.decode(token).getSubject();
    }
}
