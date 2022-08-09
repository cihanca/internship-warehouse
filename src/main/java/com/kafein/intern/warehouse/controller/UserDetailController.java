package com.kafein.intern.warehouse.controller;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.enums.Job;
import com.kafein.intern.warehouse.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userDetail")
public class UserDetailController {

    private final UserDetailService userDetailService;

    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/hire/{warehouseId}/{userId}/{job}/{salary}")
    public ResponseEntity<Boolean> hireUser(@PathVariable int warehouseId, @PathVariable int userId, @PathVariable Job job, @PathVariable int salary) {
        return new ResponseEntity<>(userDetailService.hireUser(warehouseId, userId, job, salary), HttpStatus.OK);
    }

    @PutMapping("/fire/{warehouseId}/{userId}")
    public ResponseEntity<Boolean> fireUser(@PathVariable int warehouseId, @PathVariable int userId) {
        return new ResponseEntity<>(userDetailService.fireUser(warehouseId, userId), HttpStatus.OK);
    }

    /*@GetMapping("/view/{warehouseId}")
    public ResponseEntity<UserDetailDTO> viewWarehouse(@PathVariable int warehouseId) {
        return new ResponseEntity<>(userDetailService.viewWarehouse(warehouseId), HttpStatus.OK);
    }*/

}
