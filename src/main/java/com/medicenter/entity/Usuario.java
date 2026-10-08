package com.medicenter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"))
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, length = 100) private String nome;
    @Email @NotBlank @Column(nullable = false, unique = true, length = 150) private String email;
    @NotBlank @Column(nullable = false, length = 100) private String senha;
    @NotNull @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Perfil perfil;
    @Column(nullable = false) private boolean ativo = true;

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String v) { nome = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public String getSenha() { return senha; }
    public void setSenha(String v) { senha = v; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil v) { perfil = v; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean v) { ativo = v; }
}
