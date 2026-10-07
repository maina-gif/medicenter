package com.medicenter.dto;

import com.medicenter.entity.StatusConsulta;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequest(
        @NotNull Long pacienteId,
        @NotNull Long medicoId,
        @NotNull LocalDateTime dataHoraInicio,
        @NotNull LocalDateTime dataHoraFim,
        StatusConsulta status,
        String motivo,
        String observacoes) {
}
