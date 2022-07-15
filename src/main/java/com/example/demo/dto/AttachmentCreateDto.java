package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AttachmentCreateDto {
    private String attachment;
    private UUID productId;
}
