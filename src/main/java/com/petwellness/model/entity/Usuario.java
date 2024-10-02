package com.petwellness.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petwellness.model.enums.TipoUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name= "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "apellido", length = 50)
    private String apellido;

    @Column(name = "email", length = 250)
    private String email;

    @Column(name = "telefono", length = 9)
    private String telefono;

    @Column(name = "contrasena", length = 50)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUser tipoUsuario;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RegistroMascota> mascotas;


}