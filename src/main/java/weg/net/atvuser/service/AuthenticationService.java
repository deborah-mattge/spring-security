package weg.net.atvuser.service;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import weg.net.atvuser.model.entity.User;
import weg.net.atvuser.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuarioOptional = usuarioRepository.findByUserDetailsEntity_Username(username);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get().getUserDetailsEntity();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
