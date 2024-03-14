package weg.net.atvuser.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weg.net.atvuser.model.DTO.UserDTO;
import weg.net.atvuser.model.DTO.UserUpdateDTO;
import weg.net.atvuser.model.entity.User;
import weg.net.atvuser.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

private final UserService userService;

@GetMapping("/{id}")
public User buscarUm(@PathVariable Integer id){
   return userService.findOne(id) ;
}
@GetMapping()
    public List<User> buscarTodos(){
    return userService.findAll();
}
@DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
     userService.delete(id);
}
@PostMapping("/cadastrar")
    public User cadastrar(@RequestParam String user,
                          @RequestParam MultipartFile arquivo) throws IOException {
    return userService.create(user,arquivo);
}
    @PostMapping()
    public User cadastrarUser(@RequestBody UserDTO dto){
        return userService.create(dto);
    }
@PutMapping
    public User atualizar(@RequestBody UserUpdateDTO dto) throws Exception {
    return  userService.update(dto);
}
@PatchMapping("/password/{id}")
    public  User mudarSenha(@RequestBody String password, @PathVariable Integer id){
    return userService.updatePassword(password,id);

}
@PatchMapping("/{id}")
    public User mudarAtivado(@RequestBody Boolean active, @PathVariable Integer id){
    return userService.updateActive(active, id);
}

}
