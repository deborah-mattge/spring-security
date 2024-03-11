package weg.net.atvuser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import weg.net.atvuser.service.AuthenticationService;

@Configuration
@AllArgsConstructor
public class BeanConfig {
    AuthenticationService authenticationService;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationService autenticacaoService) throws Exception{
//        auth.userDetailsService(autenticacaoService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(AuthenticationService autenticacaoService){
//        return autenticacaoService;
//    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager()  {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
     //   dao.setPasswordEncoder(new BCryptPasswordEncoder());

        dao.setUserDetailsService(authenticationService);
        return new ProviderManager(dao,dao,dao);

    }

}

