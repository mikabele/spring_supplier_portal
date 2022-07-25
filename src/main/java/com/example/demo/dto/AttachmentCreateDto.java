package com.example.demo.dto;

import com.example.demo.validation.UuidValidation;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AttachmentCreateDto {
  @NotBlank private String attachment;
  @UuidValidation @NotNull private String productId;
}
