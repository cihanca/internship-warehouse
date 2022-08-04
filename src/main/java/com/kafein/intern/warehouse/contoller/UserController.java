package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        UserDTO dto = userService.save(userDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        UserDTO dto = userService.getUser(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/getList")
    public ResponseEntity<List<UserDTO>> getListOfUsers() {
        return new ResponseEntity<List<UserDTO>>(userService.listUsers(),HttpStatus.OK);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        UserDTO dto = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

}
