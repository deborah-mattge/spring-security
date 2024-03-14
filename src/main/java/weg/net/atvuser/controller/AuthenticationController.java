package weg.net.atvuser.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import weg.net.atvuser.CookieUtil;
import weg.net.atvuser.JwtUtil;
import weg.net.atvuser.model.entity.UserLogin;
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private SecurityContextRepository contextRepository;
    private final JwtUtil jwtUtil = new JwtUtil();
    private final CookieUtil cookieUtil = new CookieUtil();
    @PostMapping("/auth/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLogin user, HttpServletRequest request, HttpServletResponse response){
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //INICIO CONTEXT - aqui vai ser salva a autenticação para que não precise a toda requisição fazer a verificação de novo, como se salva-se a sessão, mantem o usuario ativo
//            //criando contexto vazio
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            //coloca a authentication dentro do context, coloca o usuario dentro do context
//            context.setAuthentication(authentication);
//            //sabe que tem um usuario autenticado no sistema
//            contextRepository.saveContext(context, request,response );

            UserDetails user1 = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.gerarToken(user1);
            Cookie cookie = cookieUtil.gerarCookie(user1);
            response.addCookie(cookie);
            return  ResponseEntity.ok("autenticação bem sucedida");
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autorizacao");
        }

    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        try {
            Cookie cookie = cookieUtil.getCookie(request, "JWT");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }catch (Exception e){
            response.setStatus(401);
        }
    }
}
