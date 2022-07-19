package com.example.demo.repository;

import com.example.demo.domain.CategoryDomain;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryDomain, Integer> {}
