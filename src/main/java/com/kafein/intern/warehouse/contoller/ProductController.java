package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.ProductDTO;
import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.enums.RoleBasedPermission;
import com.kafein.intern.warehouse.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.save(productDTO),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("list")
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        return new ResponseEntity<>(productService.listAllProducts(), HttpStatus.OK);
    }
}
