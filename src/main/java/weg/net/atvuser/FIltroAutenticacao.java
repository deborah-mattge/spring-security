package weg.net.atvuser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import weg.net.atvuser.service.AuthenticationService;

import java.io.IOException;

public class FIltroAutenticacao extends OncePerRequestFilter{
    private CookieUtil cookieUtil = new CookieUtil();
   private SecurityContextRepository securityContextRepository;
    private AuthenticationService authenticationService;
    private JwtUtil jwtUtil = new JwtUtil();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = cookieUtil.getCookie(request,"JWT");
        String token = cookie.getValue();
        String username = jwtUtil.getUsername(token);
        UserDetails user = authenticationService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, request, response);
        filterChain.doFilter(request,response);
    }

    private boolean rotasPublica(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestURI().equals("/login") && httpServletRequest.getMethod().equals("POST");
    }
}

