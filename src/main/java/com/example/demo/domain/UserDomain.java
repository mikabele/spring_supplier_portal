package com.example.demo.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"")
public class UserDomain {
    @Id
    private UUID id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String phone;
    private String email;
    private String username;
    private String password;
}
