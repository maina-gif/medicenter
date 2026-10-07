package com.medicenter.dto;

import com.medicenter.entity.Consulta;
import com.medicenter.entity.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        Long pacienteId,
        String pacienteNome,
        Long medicoId,
        String medicoNome,
        String especialidadeNome,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        StatusConsulta status,
        String motivo,
        String observacoes) {

    public static ConsultaResponse de(Consulta c) {
        return new ConsultaResponse(
                c.getId(),
                c.getPaciente().getId(),
                c.getPaciente().getNomeCompleto(),
                c.getMedico().getId(),
                c.getMedico().getNomeCompleto(),
                c.getMedico().getEspecialidade().getNome(),
                c.getDataHoraInicio(),
                c.getDataHoraFim(),
                c.getStatus(),
                c.getMotivo(),
                c.getObservacoes());
    }
}
