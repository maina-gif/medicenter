package com.medicenter.service;

import com.medicenter.dto.MedicoRequest;
import com.medicenter.dto.MedicoResponse;
import com.medicenter.entity.Especialidade;
import com.medicenter.entity.Medico;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.EspecialidadeRepository;
import com.medicenter.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadeRepository especialidadeRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicoResponse> listar() {
        return medicoRepository.findAll().stream().map(MedicoResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public MedicoResponse buscar(Long id) {
        return MedicoResponse.de(obter(id));
    }

    @Transactional
    public MedicoResponse criar(MedicoRequest req) {
        Medico m = new Medico();
        aplicar(m, req);
        return MedicoResponse.de(medicoRepository.saveAndFlush(m));
    }

    @Transactional
    public MedicoResponse atualizar(Long id, MedicoRequest req) {
        Medico m = obter(id);
        aplicar(m, req);
        return MedicoResponse.de(medicoRepository.saveAndFlush(m));
    }

    /** Inativa em vez de apagar, para preservar o histórico de consultas. */
    @Transactional
    public void inativar(Long id) {
        Medico m = obter(id);
        m.setAtivo(false);
        medicoRepository.saveAndFlush(m);
    }

    private void aplicar(Medico m, MedicoRequest req) {
        Especialidade esp = especialidadeRepository.findById(req.especialidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Especialidade não encontrada: " + req.especialidadeId()));
        m.setNomeCompleto(req.nomeCompleto());
        m.setCrm(req.crm());
        m.setEspecialidade(esp);
        m.setTelefone(req.telefone());
        m.setEmail(req.email());
        if (req.ativo() != null) {
            m.setAtivo(req.ativo());
        }
    }

    private Medico obter(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado: " + id));
    }
}
