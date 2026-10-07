package com.medicenter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoRequest(
        @NotBlank String nomeCompleto,
        @NotBlank String crm,
        @NotNull Long especialidadeId,
        String telefone,
        @Email String email,
        Boolean ativo) {
}
