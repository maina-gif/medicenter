package com.medicenter.dto;

import com.medicenter.entity.Medico;

public record MedicoResponse(
        Long id,
        String nomeCompleto,
        String crm,
        String telefone,
        String email,
        boolean ativo,
        Long especialidadeId,
        String especialidadeNome) {

    public static MedicoResponse de(Medico m) {
        return new MedicoResponse(
                m.getId(),
                m.getNomeCompleto(),
                m.getCrm(),
                m.getTelefone(),
                m.getEmail(),
                m.isAtivo(),
                m.getEspecialidade().getId(),
                m.getEspecialidade().getNome());
    }
}
