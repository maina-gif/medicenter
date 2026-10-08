package com.medicenter.controller;

import com.medicenter.dto.UsuarioRequest;
import com.medicenter.dto.UsuarioResponse;
import com.medicenter.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/usuarios") @PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {
    private final UsuarioService service; public UsuarioController(UsuarioService service) { this.service = service; }
    @GetMapping public List<UsuarioResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public UsuarioResponse buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public UsuarioResponse criar(@Valid @RequestBody UsuarioRequest r) { return service.criar(r); }
    @PutMapping("/{id}") public UsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest r) { return service.atualizar(id, r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void inativar(@PathVariable Long id) { service.inativar(id); }
}
