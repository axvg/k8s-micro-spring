package com.example.app.micro.productservice.presentation.mapper;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.infrastructure.client.mapper.UserDtoMapper;
import com.example.app.micro.productservice.presentation.dto.CreateProductRequest;
import com.example.app.micro.productservice.presentation.dto.ProductResponse;
import com.example.app.micro.productservice.presentation.dto.UpdateProductRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring", uses = { UserDtoMapper.class })
public interface ProductDtoMapper {
    /**
     * Convierte CreateProductRequest a Product de dominio
     */
    Product toDomain(CreateProductRequest request);

    /**
     * Convierte UpdateProductRequest a Product de dominio
     */
    Product toDomain(UpdateProductRequest request);

    /**
     * Convierte Product de dominio a ProductResponse
     */
    @Mapping(target = "available", expression = "java(product.isAvailable())")
    ProductResponse toResponse(Product product);

    /**
     * Convierte lista de Products a lista de ProductResponse
     * Con el mismo mapeo para cada elemento de la lista
     */
    List<ProductResponse> toResponseList(List<Product> products);
}
