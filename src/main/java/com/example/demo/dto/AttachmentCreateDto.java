package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AttachmentCreateDto {
  @NotBlank private String attachment;
  @NotNull private UUID productId;
}
