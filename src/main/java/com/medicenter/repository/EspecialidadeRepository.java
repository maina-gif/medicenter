package com.medicenter.repository;

import com.medicenter.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    Optional<Especialidade> findByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
