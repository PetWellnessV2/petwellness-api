package com.petwellness.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class RecordatorioPK implements Serializable {
    @Column(name = "recordatorio_id")
    private Integer recordatorioId;

    @Column(name = "usuario_id")
    private Integer usuarioId;

}