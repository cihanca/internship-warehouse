package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.ProfitDetailDTO;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.ProfitDetail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    @Named("toDTO")
    ProductDetailDTO toDTO(ProductDetail productDetail);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ProductDetailDTO> toProductDTOList(List<ProductDetail> productList);

    @Named("profitToDTO")
    ProfitDetailDTO profitToDTO(ProfitDetail profitDetail);

    @IterableMapping(qualifiedByName = "profitToDTO")
    List<ProfitDetailDTO> toProfitDTOList(List<ProfitDetail> profitList);

    @Named("toEntity")
    ProductDetail toEntity(ProductDetailDTO productDetailDTO);

}
