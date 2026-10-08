package com.medicenter.repository;

import com.medicenter.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoIdAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            Long medicoId, LocalDateTime novoFim, LocalDateTime novoInicio);

    List<Consulta> findByPacienteIdAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            Long pacienteId, LocalDateTime novoFim, LocalDateTime novoInicio);
}
