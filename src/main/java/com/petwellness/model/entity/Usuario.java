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

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "contrasena", nullable = false, length = 50)
    @JsonIgnore
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUser tipoUsuario;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RegistroMascota> mascotas;
}
