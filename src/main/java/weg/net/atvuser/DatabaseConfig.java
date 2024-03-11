package weg.net.atvuser;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import weg.net.atvuser.model.entity.User;
import weg.net.atvuser.model.entity.UserDetailsEntity;
import weg.net.atvuser.repository.UserRepository;

import java.util.List;

@AllArgsConstructor
@Configuration
public class DatabaseConfig {
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        User user = new User();
        user.setName("Teste");
        user.setUserDetailsEntity(UserDetailsEntity.builder()
                .user(user).enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .username("deborah")
                        .authorities(List.of(Autorizacao.GET))
                .password(new BCryptPasswordEncoder().encode("teste123")).build());
        userRepository.save(user);
    }

}
