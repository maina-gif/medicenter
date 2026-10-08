package com.medicenter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "funcionarios", uniqueConstraints = @UniqueConstraint(name = "uk_funcionario_matricula", columnNames = "matricula"))
public class Funcionario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(name = "nome_completo", nullable = false, length = 150)
    private String nomeCompleto;
    @NotBlank @Column(nullable = false, unique = true, length = 30)
    private String matricula;
    @NotBlank @Column(nullable = false, length = 100)
    private String cargo;
    @Column(length = 20) private String telefone;
    @Email @Column(length = 150) private String email;
    @Column(nullable = false) private boolean ativo = true;

    public Long getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String v) { nomeCompleto = v; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String v) { matricula = v; }
    public String getCargo() { return cargo; }
    public void setCargo(String v) { cargo = v; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String v) { telefone = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean v) { ativo = v; }
}
