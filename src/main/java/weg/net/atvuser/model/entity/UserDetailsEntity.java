package weg.net.atvuser.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import weg.net.atvuser.Autorizacao;
import weg.net.atvuser.model.entity.User;

import java.util.Collection;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@Builder
public class UserDetailsEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,nullable = false, updatable = false)
    private String username;
    @Column(nullable = false)
    @Length(min = 6)
    private String password;
    private boolean enabled;

    private Collection<Autorizacao> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    @OneToOne(mappedBy = "userDetailsEntity")
    @JsonIgnore
    private User user;


}