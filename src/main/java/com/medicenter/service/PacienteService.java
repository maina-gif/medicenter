package com.medicenter.service;

import com.medicenter.entity.Paciente;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Paciente> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Paciente buscar(Long id) {
        return obter(id);
    }

    @Transactional
    public Paciente criar(Paciente novo) {
        return repository.saveAndFlush(novo);
    }

    @Transactional
    public Paciente atualizar(Long id, Paciente dados) {
        Paciente p = obter(id);
        p.setNomeCompleto(dados.getNomeCompleto());
        p.setCpf(dados.getCpf());
        p.setDataNascimento(dados.getDataNascimento());
        p.setTelefone(dados.getTelefone());
        p.setEmail(dados.getEmail());
        p.setAtivo(dados.isAtivo());
        return repository.saveAndFlush(p);
    }

    /** Inativa em vez de apagar, para preservar o histórico de consultas. */
    @Transactional
    public void inativar(Long id) {
        Paciente p = obter(id);
        p.setAtivo(false);
        repository.saveAndFlush(p);
    }

    private Paciente obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + id));
    }
}
