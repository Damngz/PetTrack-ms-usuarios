package com.pettrack.usuarios.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pettrack.usuarios.usuarios.models.Usuario;
import com.pettrack.usuarios.usuarios.repositories.UsuarioRepository;

@Service
public class UsuarioService {
  @Autowired
  private UsuarioRepository usuarioRepository;

  public List<Usuario> findAll() {
    return usuarioRepository.findAll();
  }

  public Optional<Usuario> findByCorreo(String correo) {
    return usuarioRepository.findByCorreo(correo);
  }

  public Optional<Usuario> findByRut(String rut) {
    return usuarioRepository.findByRut(rut);
  }

  public List<Usuario> findByRol(Usuario.Rol rol) {
    return usuarioRepository.findByRol(rol);
  }

  public Optional<Usuario> findById(Long id) {
    return usuarioRepository.findById(id);
  }

  public Usuario saveUsuario(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }
}
