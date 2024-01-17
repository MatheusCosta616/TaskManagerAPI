package controllers;

import dtos.UserRecordDto;
import jakarta.validation.Valid;
import models.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.UserRepository;

@RestController
@RequestMapping("/usuariosgeral")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);

        // Adicione lógica adicional aqui, se necessário

        UserModel savedUser = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}