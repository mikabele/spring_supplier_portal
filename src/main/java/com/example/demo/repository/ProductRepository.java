package com.example.demo.repository;

import com.example.demo.domain.ProductDomain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<ProductDomain, UUID> {
  Optional<ProductDomain> findByNameAndSupplierId(String name, Integer supplierId);

  @Query(
      value =
          "SELECT p.id, p.name, p.category_id, c.name, p.supplier_id, s.name, s.address, p.price, p.description, p.status,p.publication_date "
              + "FROM product AS p "
              + "INNER JOIN category AS c on c.id=p.category_id "
              + "INNER JOIN supplier AS s on s.id=p.supplier_id "
              + "WHERE (:name is null or p.name like :name) "
              + "AND (:category is null or c.name like :category) "
              + "AND (:description is null or p.description like :description) "
              + "AND (:supplier is null or s.name like :supplier) "
              + "AND (:minPrice is null or p.price >= :minPrice) "
              + "AND (:maxPrice is null or p.price <= :maxPrice) "
              + "AND (TO_TIMESTAMP(:startDate,'yyyy-MM-dd HH:mm:ss') is null or p.publication_date >= TO_TIMESTAMP(:startDate,'yyyy-MM-dd HH:mm:ss')) "
              + "AND (TO_TIMESTAMP(:endDate,'yyyy-MM-dd HH:mm:ss') is null or p.publication_date <= TO_TIMESTAMP(:endDate,'yyyy-MM-dd HH:mm:ss'))",
      nativeQuery = true)
  List<ProductDomain> searchByCriteria(
      String name,
      String category,
      String description,
      String supplier,
      Float minPrice,
      Float maxPrice,
      LocalDateTime startDate,
      LocalDateTime endDate);
}
