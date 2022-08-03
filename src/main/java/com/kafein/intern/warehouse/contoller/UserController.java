package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.dto.UserPublicDTO;
import com.kafein.intern.warehouse.entity.User;
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

    /**
     * lists all existing users
     *
     * @return list of all users in the database.
     */
    @GetMapping("/list")
    public ResponseEntity<List<UserPublicDTO>> list() {
        return new ResponseEntity<>(userService.listUsers(),HttpStatus.OK);
    }

    /**
     * deletes user from repo.
     *
     * @param id, takes user id to check repo
     * @return list of users in the database. If passed id exist, it removes user whose id is matched.
     */
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<List<User>> removeUser(@PathVariable int id) {
        return new  ResponseEntity<>(userService.editUserList(id),HttpStatus.OK);
    }

}
