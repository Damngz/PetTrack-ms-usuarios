package com.pettrack.usuarios.usuarios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pettrack.usuarios.usuarios.models.Usuario;
import com.pettrack.usuarios.usuarios.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {
  @Autowired
  private UsuarioService usuarioService;

  @GetMapping
  public ResponseEntity<List<Usuario>> getAllUsuarios() {
    return ResponseEntity.ok(usuarioService.findAll());
  }

  @GetMapping("/tutores")
  public ResponseEntity<List<Usuario>> getUsuariosTutores() {
    return ResponseEntity.ok(usuarioService.findByRol(Usuario.Rol.usuario));
  }

  @GetMapping("/veterinarios")
  public ResponseEntity<List<Usuario>> getUsuariosVeterinarios() {
    return ResponseEntity.ok(usuarioService.findByRol(Usuario.Rol.veterinario));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
    return usuarioService.findById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/correo/{correo}")
  public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
    return usuarioService.findByCorreo(correo)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
    return ResponseEntity.ok(usuarioService.saveUsuario(usuario));
  }
}
