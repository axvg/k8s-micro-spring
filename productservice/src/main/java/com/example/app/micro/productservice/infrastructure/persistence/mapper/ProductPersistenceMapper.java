package com.example.app.micro.productservice.infrastructure.persistence.mapper;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.infrastructure.persistence.entity.ProductEntity;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
    /**
     * Convierte ProductEntity a Product de dominio
     */
    Product toDomain(ProductEntity entity);

    /**
     * Convierte Product de dominio a ProductEntity
     */
    ProductEntity toEntity(Product product);

    /**
     * Convierte lista de ProductEntity a lista de Product
     */
    List<Product> toDomainList(List<ProductEntity> entities);
}
