package com.medicenter.service;

import com.medicenter.dto.UsuarioRequest;
import com.medicenter.dto.UsuarioResponse;
import com.medicenter.entity.Usuario;
import com.medicenter.exception.NegocioException;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) { this.repository = repository; this.passwordEncoder = passwordEncoder; }
    @Transactional(readOnly = true) public List<UsuarioResponse> listar() { return repository.findAll().stream().map(UsuarioResponse::de).toList(); }
    @Transactional(readOnly = true) public UsuarioResponse buscar(Long id) { return UsuarioResponse.de(obter(id)); }
    @Transactional public UsuarioResponse criar(UsuarioRequest r) { if (repository.existsByEmailIgnoreCase(r.email())) throw new NegocioException("E-mail já cadastrado.", org.springframework.http.HttpStatus.CONFLICT); Usuario u = new Usuario(); u.setNome(r.nome()); u.setEmail(r.email()); u.setSenha(passwordEncoder.encode(r.senha())); u.setPerfil(r.perfil()); if (r.ativo() != null) u.setAtivo(r.ativo()); return UsuarioResponse.de(repository.saveAndFlush(u)); }
    @Transactional public UsuarioResponse atualizar(Long id, UsuarioRequest r) { Usuario u = obter(id); u.setNome(r.nome()); u.setEmail(r.email()); if (r.senha() != null && !r.senha().isBlank()) u.setSenha(passwordEncoder.encode(r.senha())); u.setPerfil(r.perfil()); if (r.ativo() != null) u.setAtivo(r.ativo()); return UsuarioResponse.de(repository.saveAndFlush(u)); }
    @Transactional public void inativar(Long id) { Usuario u = obter(id); u.setAtivo(false); repository.saveAndFlush(u); }
    private Usuario obter(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id)); }
}
