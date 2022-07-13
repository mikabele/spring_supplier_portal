package com.example.demo.repository;

import com.example.demo.domain.AttachmentDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AttachmentRepository extends CrudRepository<AttachmentDomain, UUID> {
}
