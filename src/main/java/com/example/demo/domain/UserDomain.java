package com.example.demo.domain;

import com.example.demo.util.RoleEnumConverter;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"")
public class UserDomain {
  @Id private UUID id;
  private String name;
  private String surname;

  @Convert(converter = RoleEnumConverter.class)
  private Role role;

  private String phone;
  private String email;
  private String username;
  private String password;
}
