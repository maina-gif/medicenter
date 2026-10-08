package com.medicenter.service;

import com.medicenter.dto.FuncionarioRequest;
import com.medicenter.dto.FuncionarioResponse;
import com.medicenter.entity.Funcionario;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FuncionarioService {
    private final FuncionarioRepository repository;
    public FuncionarioService(FuncionarioRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true) public List<FuncionarioResponse> listar() { return repository.findAll().stream().map(FuncionarioResponse::de).toList(); }
    @Transactional(readOnly = true) public FuncionarioResponse buscar(Long id) { return FuncionarioResponse.de(obter(id)); }
    @Transactional public FuncionarioResponse criar(FuncionarioRequest req) { Funcionario f = new Funcionario(); aplicar(f, req); return FuncionarioResponse.de(repository.saveAndFlush(f)); }
    @Transactional public FuncionarioResponse atualizar(Long id, FuncionarioRequest req) { Funcionario f = obter(id); aplicar(f, req); return FuncionarioResponse.de(repository.saveAndFlush(f)); }
    @Transactional public void inativar(Long id) { Funcionario f = obter(id); f.setAtivo(false); repository.saveAndFlush(f); }
    private void aplicar(Funcionario f, FuncionarioRequest r) { f.setNomeCompleto(r.nomeCompleto()); f.setMatricula(r.matricula()); f.setCargo(r.cargo()); f.setTelefone(r.telefone()); f.setEmail(r.email()); if (r.ativo() != null) f.setAtivo(r.ativo()); }
    private Funcionario obter(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado: " + id)); }
}
