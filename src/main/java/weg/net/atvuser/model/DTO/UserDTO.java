package weg.net.atvuser.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import weg.net.atvuser.model.entity.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    private String password;
    private String username;
    private Boolean active;
    private List<File> files = new ArrayList<>();

    public void setFiles(List<MultipartFile> files) throws IOException {
        for(MultipartFile file : files){
            File f  = new File();
            f.setDados(file.getBytes());
            f.setName(file.getOriginalFilename());
            f.setType(file.getContentType());
            this.files.add(f);


        }
    }
}

