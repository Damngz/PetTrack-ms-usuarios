package com.pettrack.usuarios.usuarios.controllers;

import com.pettrack.usuarios.usuarios.models.Usuario;
import com.pettrack.usuarios.usuarios.services.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

        private MockMvc mockMvc;

        @Mock
        private UsuarioService usuarioService; 

        @InjectMocks 
        private UsuarioController controller;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                .build();
        }

        @Test
        void debeRetornarListaDeVeterinarios() throws Exception {
                // Configura usuarios de prueba
                Usuario vet1 = new Usuario();
                vet1.setId(1L);
                vet1.setNombre("Veterinario Uno");
                vet1.setRol(Usuario.Rol.veterinario);

                Usuario vet2 = new Usuario();
                vet2.setId(2L);
                vet2.setNombre("Veterinario Dos");
                vet2.setRol(Usuario.Rol.veterinario);

                // Simula la respuesta del servicio
                when(usuarioService.findByRol(Usuario.Rol.veterinario))
                                .thenReturn(Arrays.asList(vet1, vet2));

                // Ejecuta y verifica
                mockMvc.perform(get("/usuarios/veterinarios"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].nombre").value("Veterinario Uno"))
                                .andExpect(jsonPath("$[1].nombre").value("Veterinario Dos"));
        }

        @Test
        void debeRetornarTodosLosUsuarios() throws Exception {
                // Configura usuarios de prueba
                Usuario user1 = new Usuario();
                user1.setId(1L);
                user1.setNombre("Usuario Normal");
                user1.setRol(Usuario.Rol.usuario);

                Usuario user2 = new Usuario();
                user2.setId(2L);
                user2.setNombre("Admin");
                user2.setRol(Usuario.Rol.veterinario);

                // Simula la respuesta del servicio
                when(usuarioService.findAll())
                                .thenReturn(Arrays.asList(user1, user2));

                // Ejecuta y verifica
                mockMvc.perform(get("/usuarios"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].rol").value("usuario"))
                                .andExpect(jsonPath("$[1].rol").value("veterinario"));
        }

        @Test
        void debeRetornarUsuarioCuandoExisteCorreo() throws Exception {
                Usuario u = new Usuario();
                u.setId(1L);
                u.setCorreo("test@mail.com");
                u.setNombre("Test Nombre");

                when(usuarioService.findByCorreo("test@mail.com")).thenReturn(Optional.of(u));

                mockMvc.perform(get("/usuarios/correo/test@mail.com")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.correo").value("test@mail.com"))
                                .andExpect(jsonPath("$.nombre").value("Test Nombre"));
        }

        @Test
        void debeRetornar404CuandoCorreoNoExiste() throws Exception {
                when(usuarioService.findByCorreo("desconocido@mail.com")).thenReturn(Optional.empty());

                mockMvc.perform(get("/usuarios/correo/desconocido@mail.com")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        void debeCrearNuevoUsuario() throws Exception {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setId(10L);
                nuevoUsuario.setNombre("Nuevo");
                nuevoUsuario.setCorreo("nuevo@correo.com");

                // Simula que al guardar, devuelve el mismo usuario
                when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(nuevoUsuario);

                String jsonBody = """
                                {
                                  "nombre": "Nuevo",
                                  "correo": "nuevo@correo.com"
                                }
                                """;

                mockMvc.perform(post("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(10))
                                .andExpect(jsonPath("$.nombre").value("Nuevo"))
                                .andExpect(jsonPath("$.correo").value("nuevo@correo.com"));
        }

        @Test
        void debeRetornarUsuarioPorIdCuandoExiste() throws Exception {
                Usuario u = new Usuario();
                u.setId(5L);
                u.setNombre("Usuario Cinco");
                u.setCorreo("cinco@correo.com");

                when(usuarioService.findById(5L)).thenReturn(Optional.of(u));

                mockMvc.perform(get("/usuarios/5"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(5))
                                .andExpect(jsonPath("$.nombre").value("Usuario Cinco"))
                                .andExpect(jsonPath("$.correo").value("cinco@correo.com"));
        }

        @Test
        void debeRetornar404CuandoUsuarioNoExistePorId() throws Exception {
                when(usuarioService.findById(999L)).thenReturn(Optional.empty());

                mockMvc.perform(get("/usuarios/999"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void debeRetornarListaDeUsuariosTutores() throws Exception {
                Usuario tutor1 = new Usuario();
                tutor1.setId(1L);
                tutor1.setNombre("Tutor Uno");
                tutor1.setCorreo("tutor1@correo.com");
                tutor1.setRol(Usuario.Rol.usuario);

                Usuario tutor2 = new Usuario();
                tutor2.setId(2L);
                tutor2.setNombre("Tutor Dos");
                tutor2.setCorreo("tutor2@correo.com");
                tutor2.setRol(Usuario.Rol.usuario);

                List<Usuario> tutores = List.of(tutor1, tutor2);
                when(usuarioService.findByRol(Usuario.Rol.usuario)).thenReturn(tutores);

                mockMvc.perform(get("/usuarios/tutores"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].nombre").value("Tutor Uno"))
                                .andExpect(jsonPath("$[1].correo").value("tutor2@correo.com"));
        }

        @Test
        void crearUsuarioConDatosInvalidosDeberiaRetornarBadRequest() throws Exception {
                // Caso 1: Nombre vacío
                String jsonBodyNombreVacio = """
                                {
                                  "nombre": "",
                                  "correo": "valido@mail.com",
                                  "rut": "12345678-9",
                                  "telefono": "+56912345678"
                                }
                                """;

                mockMvc.perform(post("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBodyNombreVacio))
                                .andExpect(status().isBadRequest());

                // Caso 2: Correo inválido
                String jsonBodyCorreoInvalido = """
                                {
                                  "nombre": "Nombre Válido",
                                  "correo": "no-es-correo",
                                  "rut": "12345678-9"
                                }
                                """;

                mockMvc.perform(post("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBodyCorreoInvalido))
                                .andExpect(status().isBadRequest());

                // Caso 3: RUT inválido
                String jsonBodyRutInvalido = """
                                {
                                  "nombre": "Nombre Válido",
                                  "correo": "valido@mail.com",
                                  "rut": "1234"
                                }
                                """;

                mockMvc.perform(post("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBodyRutInvalido))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void obtenerUsuariosPorRolDeberiaRetornarListaCorrecta() throws Exception {

                Usuario user = new Usuario();
                user.setId(4L);
                user.setNombre("Usuario");
                user.setCorreo("usuario@pettrack.com");
                user.setRol(Usuario.Rol.usuario);

                when(usuarioService.findByRol(Usuario.Rol.usuario))
                                .thenReturn(List.of(user));

                mockMvc.perform(get("/usuarios/tutores"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1))
                                .andExpect(jsonPath("$[0].rol").value("usuario"))
                                .andExpect(jsonPath("$[0].correo").value("usuario@pettrack.com"));
        }

        @Test
        void crearUsuarioConValoresLimiteDeberiaFuncionarCorrectamente() throws Exception {

                String nombreMax = "A".repeat(50);
                String correoMax = "a".repeat(50) + "@mail.com";

                Usuario usuarioLimite = new Usuario();
                usuarioLimite.setId(100L);
                usuarioLimite.setNombre(nombreMax);
                usuarioLimite.setCorreo(correoMax);
                usuarioLimite.setRut("99999999-9");

                when(usuarioService.saveUsuario(any(Usuario.class)))
                                .thenReturn(usuarioLimite);

                String jsonBody = String.format("""
                                {
                                  "nombre": "%s",
                                  "correo": "%s",
                                  "rut": "99999999-9"
                                }
                                """, nombreMax, correoMax);

                mockMvc.perform(post("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value(nombreMax))
                                .andExpect(jsonPath("$.correo").value(correoMax));
        }
}
