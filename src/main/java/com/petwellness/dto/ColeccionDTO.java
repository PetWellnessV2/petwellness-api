package com.petwellness.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ColeccionDTO {
    private Integer id;
    private String nombre;
    private Integer clienteId;
    private Set<Integer> productosIds;
}