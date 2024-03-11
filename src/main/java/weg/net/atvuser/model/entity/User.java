package weg.net.atvuser.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
//    private String password;
//    private String username;
    private Boolean enabled;
    @OneToMany(cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)

    private UserDetailsEntity userDetailsEntity;
}
