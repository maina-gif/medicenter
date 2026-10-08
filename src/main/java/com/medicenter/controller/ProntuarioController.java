package com.medicenter.controller;

import com.medicenter.dto.ProntuarioRequest;
import com.medicenter.dto.ProntuarioResponse;
import com.medicenter.service.ProntuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/prontuarios")
public class ProntuarioController {
    private final ProntuarioService service; public ProntuarioController(ProntuarioService service) { this.service = service; }
    @GetMapping public List<ProntuarioResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public ProntuarioResponse buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ProntuarioResponse criar(@Valid @RequestBody ProntuarioRequest r) { return service.criar(r); }
    @PutMapping("/{id}") public ProntuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProntuarioRequest r) { return service.atualizar(id, r); }
}
