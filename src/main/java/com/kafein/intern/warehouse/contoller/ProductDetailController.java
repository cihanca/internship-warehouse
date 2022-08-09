package com.kafein.intern.warehouse.contoller;
;
import com.kafein.intern.warehouse.dto.ProductDetailDTO;

import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.dto.ProductUpdateDTO;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.service.ProductDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDetailDTO> save(@RequestBody ProductDetailDTO productDetailDTO) {
        return new ResponseEntity<>(productDetailService.save(productDetailDTO), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDetailDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(productDetailService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ProductDetailFilterDTO filterDTO) {
        return new ResponseEntity<>(productDetailService.filter(filterDTO), HttpStatus.OK);
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<?> removeProductFromWarehouse(@RequestBody ProductUpdateDTO productUpdateDTO) {
        return new ResponseEntity<>(productDetailService.
                updateProductAtWarehouse(productUpdateDTO), HttpStatus.OK);
    }

}
