package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Customer customer;

    @OneToOne(mappedBy = "vet",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Veterinario veterinario;

    @OneToOne(mappedBy = "admin",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Albergue admin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}