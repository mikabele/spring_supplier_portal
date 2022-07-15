package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AttachmentReadDto {
    private UUID id;
    private String attachment;
}
