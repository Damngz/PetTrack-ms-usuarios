package com.pettrack.usuarios.usuarios.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pettrack.usuarios.usuarios.models.Usuario;
import com.pettrack.usuarios.usuarios.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)

 class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1, usuario2;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");
        usuario1.setCorreo("juan@test.com");
        usuario1.setApellido("Gómez");
        usuario1.setDireccion("Avenida Siempreviva 742");
        usuario1.setRol(Usuario.Rol.usuario);

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Ana");
        usuario2.setCorreo("ana@test.com");
        usuario2.setApellido("Galvez");
        usuario2.setDireccion("Avenida Argentina 82");
        usuario2.setRol(Usuario.Rol.veterinario);
    }

    @Test
    void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> result = usuarioService.findAll();

        assertEquals(2, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindByCorreo() {
        when(usuarioRepository.findByCorreo("juan@test.com")).thenReturn(Optional.of(usuario1));

        Optional<Usuario> result = usuarioService.findByCorreo("juan@test.com");

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
    }

    @Test
    void testFindByRut() {
        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Optional.of(usuario1));

        Optional<Usuario> result = usuarioService.findByRut("12345678-9");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindByRol() {
        when(usuarioRepository.findByRol(Usuario.Rol.usuario)).thenReturn(Arrays.asList(usuario1));

        List<Usuario> result = usuarioService.findByRol(Usuario.Rol.usuario);

        assertEquals(1, result.size());
        assertEquals("usuario", result.get(0).getRol().name());
    }

    @Test
    void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));

        Optional<Usuario> result = usuarioService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("juan@test.com", result.get().getCorreo());
    }

    @Test
    void testSaveUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        Usuario saved = usuarioService.saveUsuario(usuario1);

        assertEquals(1L, saved.getId());
        verify(usuarioRepository, times(1)).save(usuario1);
    }

}
