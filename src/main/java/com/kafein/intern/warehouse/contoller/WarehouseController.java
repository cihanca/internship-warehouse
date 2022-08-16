package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/save")
    public ResponseEntity<WarehouseDTO> save(@RequestBody WarehouseDTO warehouseDTO) {
        WarehouseDTO dto = warehouseService.save(warehouseDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable int id) {
        WarehouseDTO dto = warehouseService.getWarehouse(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/getList")
    public ResponseEntity<List<WarehouseDTO>> getListOfWarehouses() {
        return new ResponseEntity<>(warehouseService.listWarehouses(),HttpStatus.OK);
    }

    /*@PutMapping("/change/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        UserDTO dto = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return getListOfUsers();
    }*/
}
