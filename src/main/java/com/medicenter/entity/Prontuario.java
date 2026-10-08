package com.medicenter.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prontuarios", uniqueConstraints = @UniqueConstraint(name = "uk_prontuario_consulta", columnNames = "consulta_id"))
public class Prontuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "consulta_id", nullable = false, unique = true)
    private Consulta consulta;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    @Column(name = "data_atendimento", nullable = false) private LocalDateTime dataAtendimento;
    @Column(length = 1000) private String queixaPrincipal;
    @Column(length = 2000) private String historico;
    @Column(length = 2000) private String diagnostico;
    @Column(length = 2000) private String conduta;
    @Column(length = 2000) private String prescricao;
    @Column(length = 2000) private String observacoes;

    public Long getId() { return id; }
    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta v) { consulta = v; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente v) { paciente = v; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico v) { medico = v; }
    public LocalDateTime getDataAtendimento() { return dataAtendimento; }
    public void setDataAtendimento(LocalDateTime v) { dataAtendimento = v; }
    public String getQueixaPrincipal() { return queixaPrincipal; }
    public void setQueixaPrincipal(String v) { queixaPrincipal = v; }
    public String getHistorico() { return historico; }
    public void setHistorico(String v) { historico = v; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String v) { diagnostico = v; }
    public String getConduta() { return conduta; }
    public void setConduta(String v) { conduta = v; }
    public String getPrescricao() { return prescricao; }
    public void setPrescricao(String v) { prescricao = v; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String v) { observacoes = v; }
}
