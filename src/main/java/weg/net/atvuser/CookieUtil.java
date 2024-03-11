package weg.net.atvuser;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class CookieUtil {
    public Cookie gerarCookie(UserDetails userDetails){
        String token =new JwtUtil().gerarToken(userDetails);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setMaxAge(300);
        return cookie;

    }
    public Cookie getCookie(HttpServletRequest request, String name){
        return WebUtils.getCookie(request, name);
    }
}
