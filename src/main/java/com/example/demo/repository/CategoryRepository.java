package com.example.demo.repository;

import com.example.demo.domain.CategoryDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends CrudRepository<CategoryDomain, Integer> {
}
