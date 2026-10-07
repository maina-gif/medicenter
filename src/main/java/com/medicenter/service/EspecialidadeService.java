package com.medicenter.service;

import com.medicenter.entity.Especialidade;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Especialidade> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Especialidade buscar(Long id) {
        return obter(id);
    }

    @Transactional
    public Especialidade criar(Especialidade nova) {
        return repository.saveAndFlush(nova);
    }

    @Transactional
    public Especialidade atualizar(Long id, Especialidade dados) {
        Especialidade e = obter(id);
        e.setNome(dados.getNome());
        e.setDescricao(dados.getDescricao());
        return repository.saveAndFlush(e);
    }

    /** Se houver médicos usando a especialidade, o banco recusa e a API responde 409. */
    @Transactional
    public void excluir(Long id) {
        repository.delete(obter(id));
        repository.flush();
    }

    private Especialidade obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Especialidade não encontrada: " + id));
    }
}
