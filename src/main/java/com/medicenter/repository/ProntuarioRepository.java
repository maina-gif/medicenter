package com.medicenter.repository;

import com.medicenter.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Optional<Prontuario> findByConsultaId(Long consultaId);
    boolean existsByConsultaId(Long consultaId);
}
