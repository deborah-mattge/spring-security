package weg.net.atvuser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import weg.net.atvuser.service.AuthenticationService;

import java.io.IOException;
@Component
@AllArgsConstructor


public class FIltroAutenticacao extends OncePerRequestFilter{
    private final CookieUtil cookieUtil = new CookieUtil();
   private SecurityContextRepository securityContextRepository;
    private AuthenticationService authenticationService;
    private final JwtUtil jwtUtil = new JwtUtil();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!rotasPublica(request)) {
            Cookie cookie;
            try{
                cookie = cookieUtil.getCookie(request, "JWT");

            }catch (Exception e){
                response.setStatus(401);
                return;
            }
            String token = cookie.getValue();
            String username = jwtUtil.getUsername(token);
            UserDetails user = authenticationService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);
            Cookie cookieRenovado  = cookieUtil.gerarCookie(user);
            response.addCookie(cookieRenovado);
        }
            filterChain.doFilter(request, response);

    }

    private boolean rotasPublica(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestURI().equals("/auth/login") && httpServletRequest.getMethod().equals("POST");
    }
}

