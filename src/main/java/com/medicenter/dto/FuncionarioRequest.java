package com.medicenter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FuncionarioRequest(@NotBlank String nomeCompleto, @NotBlank String matricula, @NotBlank String cargo,
                                 String telefone, @Email String email, Boolean ativo) {}
