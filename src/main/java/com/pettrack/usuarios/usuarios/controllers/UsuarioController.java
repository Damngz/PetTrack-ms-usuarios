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
import com.pettrack.usuarios.usuarios.repositories.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @GetMapping("/tutores")
  public ResponseEntity<List<Usuario>> getUsuariosTutores() {
    return ResponseEntity.ok(usuarioRepository.findByRol(Usuario.Rol.usuario));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
    return usuarioRepository.findById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/correo/{correo}")
  public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
    return usuarioRepository.findByCorreo(correo)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
    return ResponseEntity.ok(usuarioRepository.save(usuario));
  }
}
