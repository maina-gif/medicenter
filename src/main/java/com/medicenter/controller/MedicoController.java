package com.medicenter.controller;

import com.medicenter.dto.MedicoRequest;
import com.medicenter.dto.MedicoResponse;
import com.medicenter.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<MedicoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MedicoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoResponse criar(@Valid @RequestBody MedicoRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public MedicoResponse atualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        service.inativar(id);
    }
}
