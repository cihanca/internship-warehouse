package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.request.WarehouseCreateRequest;
import com.kafein.intern.warehouse.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouse")
public class WarehouseController {

    private final WarehouseService wareHouseService;

    public WarehouseController(WarehouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @PostMapping("/save")
    public ResponseEntity<WarehouseDTO> save(@RequestBody WarehouseCreateRequest warehouseCreateRequest) {
        return new ResponseEntity<>(wareHouseService.save(warehouseCreateRequest), HttpStatus.OK);
    }
}
