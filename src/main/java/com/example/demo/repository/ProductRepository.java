package com.example.demo.repository;

import com.example.demo.domain.ProductDomain;
import com.example.demo.dto.ProductCreateDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<ProductDomain, UUID> {
    Optional<ProductDomain> findByNameAndSupplierId(String name, Integer supplierId);

    //TODO add methods with join
    @Query("")
    List<ProductDomain> searchByCriteria(@Param("name") Optional<String> name,
                                         @Param("categoryName") Optional<String> categoryName,
                                         @Param("description") Optional<String> description);
}
