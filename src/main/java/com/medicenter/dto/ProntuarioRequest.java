package com.medicenter.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ProntuarioRequest(@NotNull Long consultaId, LocalDateTime dataAtendimento, String queixaPrincipal,
                                String historico, String diagnostico, String conduta, String prescricao, String observacoes) {}
