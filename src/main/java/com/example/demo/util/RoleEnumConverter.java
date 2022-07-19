package com.example.demo.util;

import com.example.demo.domain.Role;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class RoleEnumConverter implements AttributeConverter<Role, String> {

  @Override
  public String convertToDatabaseColumn(Role role) {
    return role.toString().toLowerCase();
  }

  @Override
  public Role convertToEntityAttribute(String s) {
    return Role.valueOf(s.toUpperCase());
  }
}
