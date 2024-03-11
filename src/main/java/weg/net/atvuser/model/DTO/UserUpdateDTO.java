package weg.net.atvuser.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private Integer id;

    private String name;
    private String password;
    private String username;
    private Boolean active;
}
