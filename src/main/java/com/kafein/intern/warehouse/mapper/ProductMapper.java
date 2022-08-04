package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.ProductDTO;
import com.kafein.intern.warehouse.entity.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Named("toDTO")
    ProductDTO toDTO(Product product);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ProductDTO> toProductDTOList(List<Product> productList);

    @Named("toEntity")
    Product toEntity(ProductDTO productDTO);


}

