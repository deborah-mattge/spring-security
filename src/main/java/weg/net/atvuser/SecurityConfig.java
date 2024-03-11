package weg.net.atvuser;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import weg.net.atvuser.model.entity.User;
import weg.net.atvuser.model.entity.UserDetailsEntity;
import weg.net.atvuser.repository.UserRepository;
import weg.net.atvuser.service.AuthenticationService;

@Configuration
@AllArgsConstructor
public class SecurityConfig {


    private final SecurityContextRepository repo;


//    @Bean
//    public SecurityFilterChain config(HttpSecurity http)throws Exception{
//        http.authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers
//                        (HttpMethod.GET,"/user").hasAuthority("Get")
//                .requestMatchers(HttpMethod.GET,"user/users").permitAll()
//                .anyRequest().authenticated()
//        );
//        http.formLogin(Customizer.withDefaults());
//        http.logout(Customizer.withDefaults());
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain config(HttpSecurity http)throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers
                        (HttpMethod.GET,"/user").hasAuthority("Get")
                .requestMatchers(HttpMethod.GET,"user/users").permitAll()
                .anyRequest().authenticated()
        );
        http.securityContext((context)->context.securityContextRepository(repo));
        http.formLogin(Customizer.withDefaults());
        http.logout(Customizer.withDefaults());
        return http.build();

    }






//      UserDetails user = User.withDefaultPasswordEncoder()
//              .username("mi72")
//              .password("M!7dois")
//              .build();
//      return new InMemoryUserDetailsManager(user);
//    }

}
