package com.kafein.intern.warehouse.controller;
import com.kafein.intern.warehouse.dto.ProcessDetailFilterDTO;
import com.kafein.intern.warehouse.dto.ProductDetailDTO;

import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.service.ProductDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kafein.intern.warehouse.enums.ProcessType.ADD_PRODUCT;
import static com.kafein.intern.warehouse.enums.ProcessType.DELETE_PRODUCT;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @GetMapping("/getSoldCount/{productDetailId}")
    public ResponseEntity<Integer> getSoldCount(@PathVariable int productDetailId) {
        return new ResponseEntity<>(productDetailService.getSoldCount(productDetailId), HttpStatus.OK);
    }

    @GetMapping("/getBoughtCount/{productDetailId}")
    public ResponseEntity<Integer> getBoughtCount(@PathVariable int productDetailId) {
        return new ResponseEntity<>(productDetailService.getBoughtCount(productDetailId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDetailDTO> save(@RequestBody ProductDetailDTO productDetailDTO) {
        return new ResponseEntity<>(productDetailService.save(productDetailDTO), HttpStatus.OK);
    }

    @PostMapping("/add/{warehouseId}/{productId}/{count}")
    public ResponseEntity<Boolean> addProductToWarehouse(@PathVariable int warehouseId, @PathVariable int productId, @PathVariable int count){
        return new ResponseEntity<>(productDetailService.addProductToWarehouse(warehouseId, productId,count), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(productDetailService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/filterProducts")
    public ResponseEntity<?> filterProducts(@RequestBody ProductDetailFilterDTO filterDTO) {
        return new ResponseEntity<>(productDetailService.filterProducts(filterDTO), HttpStatus.OK);
    }

    @PostMapping("/filterProcesses")
    public ResponseEntity<?> filterProcesses(@RequestBody ProcessDetailFilterDTO filterDTO) {
        return new ResponseEntity<>(productDetailService.filterProcesses(filterDTO), HttpStatus.OK);
    }

    @PutMapping("/remove/{warehouseId}/{productId}/{count}")
    public ResponseEntity<?> removeProductFromWarehouse(@PathVariable int warehouseId, @PathVariable int productId, @PathVariable int count) {
        return new ResponseEntity<>(productDetailService.removeProductFromWarehouse(warehouseId, productId, count), HttpStatus.OK);
    }

}
