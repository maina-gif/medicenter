package com.medicenter.controller;

import com.medicenter.dto.ConsultaRequest;
import com.medicenter.dto.ConsultaResponse;
import com.medicenter.entity.StatusConsulta;
import com.medicenter.service.ConsultaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    public record StatusRequest(@NotNull StatusConsulta status) {}

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConsultaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ConsultaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse criar(@Valid @RequestBody ConsultaRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public ConsultaResponse atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    public ConsultaResponse alterarStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return service.alterarStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
