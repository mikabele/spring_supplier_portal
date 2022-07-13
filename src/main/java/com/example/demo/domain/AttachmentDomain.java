package com.example.demo.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class AttachmentDomain {
    private UUID id;
    private String attachment;
    private UUID productId;
}
