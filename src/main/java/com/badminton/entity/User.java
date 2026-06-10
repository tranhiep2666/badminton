package com.badminton.entity;

import jakarta.persistence.*;
import lombok.*;
import com.badminton.enums.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,
            unique = true)
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean enabled = true;
}
