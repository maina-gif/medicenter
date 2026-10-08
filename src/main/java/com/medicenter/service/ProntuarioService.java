package com.medicenter.service;

import com.medicenter.dto.ProntuarioRequest;
import com.medicenter.dto.ProntuarioResponse;
import com.medicenter.entity.Consulta;
import com.medicenter.entity.Prontuario;
import com.medicenter.entity.StatusConsulta;
import com.medicenter.exception.NegocioException;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.ConsultaRepository;
import com.medicenter.repository.ProntuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProntuarioService {
    private final ProntuarioRepository repository; private final ConsultaRepository consultaRepository;
    public ProntuarioService(ProntuarioRepository repository, ConsultaRepository consultaRepository) { this.repository = repository; this.consultaRepository = consultaRepository; }
    @Transactional(readOnly = true) public List<ProntuarioResponse> listar() { return repository.findAll().stream().map(ProntuarioResponse::de).toList(); }
    @Transactional(readOnly = true) public ProntuarioResponse buscar(Long id) { return ProntuarioResponse.de(obter(id)); }
    @Transactional public ProntuarioResponse criar(ProntuarioRequest r) { Consulta c = consultaRepository.findById(r.consultaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + r.consultaId())); if (c.getStatus() != StatusConsulta.REALIZADA) throw new NegocioException("O prontuário só pode ser criado para uma consulta REALIZADA."); if (repository.existsByConsultaId(c.getId())) throw new NegocioException("A consulta já possui prontuário.", org.springframework.http.HttpStatus.CONFLICT); Prontuario p = new Prontuario(); p.setConsulta(c); p.setPaciente(c.getPaciente()); p.setMedico(c.getMedico()); aplicar(p, r); return ProntuarioResponse.de(repository.saveAndFlush(p)); }
    @Transactional public ProntuarioResponse atualizar(Long id, ProntuarioRequest r) { Prontuario p = obter(id); aplicar(p, r); return ProntuarioResponse.de(repository.saveAndFlush(p)); }
    private void aplicar(Prontuario p, ProntuarioRequest r) { p.setDataAtendimento(r.dataAtendimento() == null ? LocalDateTime.now() : r.dataAtendimento()); p.setQueixaPrincipal(r.queixaPrincipal()); p.setHistorico(r.historico()); p.setDiagnostico(r.diagnostico()); p.setConduta(r.conduta()); p.setPrescricao(r.prescricao()); p.setObservacoes(r.observacoes()); }
    private Prontuario obter(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Prontuário não encontrado: " + id)); }
}
