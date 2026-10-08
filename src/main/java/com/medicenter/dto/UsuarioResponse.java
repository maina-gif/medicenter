package com.medicenter.dto;

import com.medicenter.entity.Usuario;
import com.medicenter.entity.Perfil;

public record UsuarioResponse(Long id, String nome, String email, Perfil perfil, boolean ativo) {
    public static UsuarioResponse de(Usuario u) { return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getPerfil(), u.isAtivo()); }
}
