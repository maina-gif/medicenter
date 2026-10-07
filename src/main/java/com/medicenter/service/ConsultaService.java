package com.medicenter.service;

import com.medicenter.dto.ConsultaRequest;
import com.medicenter.dto.ConsultaResponse;
import com.medicenter.entity.Consulta;
import com.medicenter.entity.Medico;
import com.medicenter.entity.Paciente;
import com.medicenter.entity.StatusConsulta;
import com.medicenter.exception.NegocioException;
import com.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.repository.ConsultaRepository;
import com.medicenter.repository.MedicoRepository;
import com.medicenter.repository.PacienteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listar() {
        return consultaRepository.findAll(Sort.by("dataHoraInicio")).stream()
                .map(ConsultaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ConsultaResponse buscar(Long id) {
        return ConsultaResponse.de(obter(id));
    }

    @Transactional
    public ConsultaResponse criar(ConsultaRequest req) {
        Consulta c = new Consulta();
        aplicar(c, req);
        validar(c, true);
        return ConsultaResponse.de(consultaRepository.saveAndFlush(c));
    }

    @Transactional
    public ConsultaResponse atualizar(Long id, ConsultaRequest req) {
        Consulta c = obter(id);
        aplicar(c, req);
        validar(c, false);
        return ConsultaResponse.de(consultaRepository.saveAndFlush(c));
    }

    @Transactional
    public ConsultaResponse alterarStatus(Long id, StatusConsulta novoStatus) {
        Consulta c = obter(id);
        c.setStatus(novoStatus);
        validar(c, false); // reativar uma consulta cancelada precisa rechecar conflitos
        return ConsultaResponse.de(consultaRepository.saveAndFlush(c));
    }

    @Transactional
    public void excluir(Long id) {
        consultaRepository.delete(obter(id));
    }

    // ------------------------------------------------------------------

    private void aplicar(Consulta c, ConsultaRequest req) {
        Paciente paciente = pacienteRepository.findById(req.pacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + req.pacienteId()));
        Medico medico = medicoRepository.findById(req.medicoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado: " + req.medicoId()));
        c.setPaciente(paciente);
        c.setMedico(medico);
        c.setDataHoraInicio(req.dataHoraInicio());
        c.setDataHoraFim(req.dataHoraFim());
        if (req.status() != null) {
            c.setStatus(req.status());
        }
        c.setMotivo(req.motivo());
        c.setObservacoes(req.observacoes());
    }

    private void validar(Consulta c, boolean nova) {
        if (!c.getDataHoraFim().isAfter(c.getDataHoraInicio())) {
            throw new NegocioException("O horário de fim deve ser posterior ao de início.");
        }
        if (nova && !c.getPaciente().isAtivo()) {
            throw new NegocioException("Paciente inativo não pode ter consulta agendada.");
        }
        if (nova && !c.getMedico().isAtivo()) {
            throw new NegocioException("Médico inativo não pode ter consulta agendada.");
        }
        if (c.getStatus() == StatusConsulta.CANCELADA) {
            return; // consulta cancelada não ocupa horário
        }

        boolean conflitoMedico = consultaRepository
                .findByMedicoIdAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                        c.getMedico().getId(), c.getDataHoraFim(), c.getDataHoraInicio())
                .stream().anyMatch(o -> ocupaHorario(o, c));
        if (conflitoMedico) {
            throw new NegocioException("O médico já possui consulta nesse horário.", HttpStatus.CONFLICT);
        }

        boolean conflitoPaciente = consultaRepository
                .findByPacienteIdAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                        c.getPaciente().getId(), c.getDataHoraFim(), c.getDataHoraInicio())
                .stream().anyMatch(o -> ocupaHorario(o, c));
        if (conflitoPaciente) {
            throw new NegocioException("O paciente já possui consulta nesse horário.", HttpStatus.CONFLICT);
        }
    }

    /** Outra consulta (não a própria, não cancelada) que ocupa o horário. */
    private boolean ocupaHorario(Consulta outra, Consulta atual) {
        return !Objects.equals(outra.getId(), atual.getId())
                && outra.getStatus() != StatusConsulta.CANCELADA;
    }

    private Consulta obter(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + id));
    }
}
