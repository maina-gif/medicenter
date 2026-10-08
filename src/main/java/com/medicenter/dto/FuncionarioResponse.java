package com.medicenter.dto;

import com.medicenter.entity.Funcionario;

public record FuncionarioResponse(Long id, String nomeCompleto, String matricula, String cargo,
                                  String telefone, String email, boolean ativo) {
    public static FuncionarioResponse de(Funcionario f) {
        return new FuncionarioResponse(f.getId(), f.getNomeCompleto(), f.getMatricula(), f.getCargo(), f.getTelefone(), f.getEmail(), f.isAtivo());
    }
}
