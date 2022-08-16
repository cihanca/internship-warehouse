package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.dto.UserPublicDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

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

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/list")
    public ResponseEntity<List<UserPublicDTO>> list() {
        return new ResponseEntity<>(userService.listUsers(),HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<List<UserPublicDTO>> removeUser(@PathVariable int id) {
        return new  ResponseEntity<>(userService.editUserList(id),HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{password}")
    public ResponseEntity <UserPublicDTO> updateUserPassword ( @PathVariable int id, @PathVariable String password) {
        return new ResponseEntity<>(userService.updateUserPassword(id, password), HttpStatus.OK);
    }
}
