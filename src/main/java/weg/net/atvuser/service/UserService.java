package weg.net.atvuser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weg.net.atvuser.model.DTO.UserDTO;
import weg.net.atvuser.model.DTO.UserUpdateDTO;
import weg.net.atvuser.model.entity.User;
import weg.net.atvuser.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private  final ObjectMapper objectMapper;


    public User findOne(Integer id){
        return userRepository.findById(id).get();
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public void delete(Integer id){
        userRepository.deleteById(id);
    }
    public User create(UserDTO dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return userRepository.save(user);
    }
    public User create(String user, MultipartFile arquivos) throws IOException {

        UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
        userDTO.setFiles(List.of(arquivos));
        return create(userDTO);

    }
    public User update(UserUpdateDTO dto) throws Exception{
        if(!userRepository.existsById(dto.getId())){
            throw new Exception("Não foi encontrado " +
                    "nenhum usuario "+
                    dto.getId() + ".");
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return userRepository.save(user);
    }
    public User updatePassword(String password, Integer id){
      User user = userRepository.findById(id).get();
       // user.setPassword(password);
        return userRepository.save(user);

    }
    public User updateActive(Boolean active, Integer id){
        User user = userRepository.findById(id).get();
        // user.setActive(active);
        return userRepository.save(user);

    }


}
