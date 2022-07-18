package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionDto {
	private String devMessage;
	private String userMessage;
	private int errorCode;
}
