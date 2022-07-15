package com.example.demo.repository;

import com.example.demo.domain.SupplierDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SupplierRepository extends CrudRepository<SupplierDomain,Integer> {
}
