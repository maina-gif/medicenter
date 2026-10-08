package com.medicenter.dto;

import com.medicenter.entity.Prontuario;
import java.time.LocalDateTime;

public record ProntuarioResponse(Long id, Long consultaId, Long pacienteId, String pacienteNome, Long medicoId,
                                 String medicoNome, LocalDateTime dataAtendimento, String queixaPrincipal,
                                 String historico, String diagnostico, String conduta, String prescricao, String observacoes) {
    public static ProntuarioResponse de(Prontuario p) {
        return new ProntuarioResponse(p.getId(), p.getConsulta().getId(), p.getPaciente().getId(), p.getPaciente().getNomeCompleto(),
                p.getMedico().getId(), p.getMedico().getNomeCompleto(), p.getDataAtendimento(), p.getQueixaPrincipal(),
                p.getHistorico(), p.getDiagnostico(), p.getConduta(), p.getPrescricao(), p.getObservacoes());
    }
}
