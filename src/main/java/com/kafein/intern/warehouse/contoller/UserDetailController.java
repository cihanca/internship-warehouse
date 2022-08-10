package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.dto.UserDetailFilterDTO;
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

    @PostMapping("/hireToWarehouse")
    public ResponseEntity<?> addEmployeeToWarehouse(@RequestBody UserDetailDTO userDetailDTO) {
        return new ResponseEntity<>(userDetailService.addEmployeeToWarehouse(userDetailDTO), HttpStatus.OK);
    }

    @PostMapping("/fireFromWarehouse")
    public ResponseEntity<?> removeEmployeeFromWarehouse(@RequestBody UserDetailDTO userDetailDTO) {
        return new ResponseEntity<>(userDetailService.removeEmployeeFromWarehouse(userDetailDTO), HttpStatus.OK);
    }

    @PostMapping("filter")
    public ResponseEntity<?> filterAccordingly(@RequestBody UserDetailFilterDTO userDetailFilterDTO) {
        return new ResponseEntity<>(userDetailService.filter(userDetailFilterDTO), HttpStatus.OK);
    }

    @GetMapping("/getEmployeeNumber/{warehouseId}")
    public ResponseEntity<?> getEmployeeNumber(@PathVariable int warehouseId) {
        return new ResponseEntity<>(userDetailService.getNumberOfEmployeesAtWarehouse(warehouseId), HttpStatus.OK);
    }

}
