package com.medicenter.controller;

import com.medicenter.dto.FuncionarioRequest;
import com.medicenter.dto.FuncionarioResponse;
import com.medicenter.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/funcionarios") @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
public class FuncionarioController {
    private final FuncionarioService service; public FuncionarioController(FuncionarioService service) { this.service = service; }
    @GetMapping public List<FuncionarioResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public FuncionarioResponse buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('ADMIN')") public FuncionarioResponse criar(@Valid @RequestBody FuncionarioRequest r) { return service.criar(r); }
    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public FuncionarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioRequest r) { return service.atualizar(id, r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("hasRole('ADMIN')") public void inativar(@PathVariable Long id) { service.inativar(id); }
}
