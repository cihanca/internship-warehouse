package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        UserDTO dto = userService.getUser(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        UserDTO dto = userService.save(userDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
