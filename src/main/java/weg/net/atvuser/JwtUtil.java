package weg.net.atvuser;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtil {

    //criar metodo de gerar senha
    public String gerarToken(UserDetails userDetails){
       return Jwts.builder().issuer("WEG")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+300000))
                .signWith(SignatureAlgorithm.NONE, "senha123")
                .subject(userDetails.getUsername()).
                compact();
    }
    public void validarToken(String token){
       var parser = getParser();
     parser.parseSignedClaims(token);
    }
    public String getUSername(String token){

        return getParser().parseSignedClaims(token).getPayload().getSubject();
    }
    public JwtParser getParser(){
        return  Jwts.parser().setSigningKey("senha123").build();
    }
}
