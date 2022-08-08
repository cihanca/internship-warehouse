package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class UserDetailController {

    private final UserDetailService userDetailService;

    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/save")
    public ResponseEntity<UserDetailDTO> save(@RequestBody UserDetailDTO userDetailDTO) {
        return new ResponseEntity<>(userDetailService.save(userDetailDTO), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDetailDTO> get(@PathVariable int id) {
        return new ResponseEntity<>(userDetailService.get(id),HttpStatus.OK);
    }
}
