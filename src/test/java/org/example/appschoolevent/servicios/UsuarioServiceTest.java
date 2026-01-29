package org.example.appschoolevent.servicios;


import jakarta.transaction.Transactional;
import org.example.appschoolevent.DTO.UsuarioDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.exceptions.OperacionNoPermitida;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @BeforeEach
    void cargarDatos(){
        usuarioRepository.deleteAll();
        eventoRepository.deleteAll();
        inscripcionRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setNombre("Luis");
        usuario.setApellido("Cabello");
        usuario.setContrasena("pelones");
        usuario.setFecha_de_nacimiento(LocalDate.of(2006, 12, 13));
        usuario.setCorreo("lcabellocabanillas@safareyes.es");
        usuario.setTipo(Usuario.TipoUsuario.User);
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Luis");
        usuario2.setApellido("Cabello");
        usuario2.setContrasena("calvos");
        usuario2.setFecha_de_nacimiento(LocalDate.of(2006, 12, 13));
        usuario2.setCorreo("lcabellocabanilla@safareyes.es");
        usuario2.setTipo(Usuario.TipoUsuario.Admin);
        usuarioRepository.save(usuario2);


        Evento u = new Evento();
        u.setNombre("admin");
        u.setRequisitos("NINGUNO");
        u.setConsiste("Comida");
        u.setInscripciones(new HashSet<>());
        u.setFecha(LocalDate.now());
        u.setCategoria(TipoCategoria.Deportivo);
        u.setLugar("Casa");
        eventoRepository.save(u);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(u);
        inscripcionRepository.save(inscripcion);

    }

    @Test
    @DisplayName("Servicio 1 -> Caso Positivo")
    public void registrarUsuario() {

        //Given
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Luis");
        dto.setApellido("Cabello");
        dto.setContrasena("calvos");
        dto.setFecha_de_nacimiento(LocalDate.ofEpochDay(13/12/2006));
        dto.setCorreo("jnuevocorreo@gmail.com");
        dto.setTipo(String.valueOf(Usuario.TipoUsuario.Admin));

        //Then
        UsuarioDTO resultado = usuarioService.registrarUsuario(dto);

        //When
        assertNotNull(resultado);
        assertEquals("jnuevocorreo@gmail.com", resultado.getCorreo());
    }

    @Test
    @DisplayName("Servicio 1 -> Negativo")
    void registrarUsuarioConCorreoExistente(){

        //Given
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Luis");
        dto.setApellido("Cabello");
        dto.setContrasena("calvos");
        dto.setFecha_de_nacimiento(LocalDate.ofEpochDay(13/12/2006));
        dto.setCorreo("lcabellocabanillas@safareyes.es");
        dto.setTipo(String.valueOf(Usuario.TipoUsuario.Admin));

        //Then

        //When
        assertThrows(OperacionNoPermitida.class, () -> usuarioService.registrarUsuario(dto) );
    }

    @Test
    @DisplayName("Servicio 8 -> Caso Positivo")
    void obtenerEventosDeUsuario(){

        Usuario usuario = usuarioRepository.findAll().get(0);
        Integer idUsuario = usuario.getId();

        //Then
        var lista = usuarioService.obtenerEventosDeUsuario(idUsuario);

        //When
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Servicio 8 -> Caso Negativo")
    void obtenerEventosDeUsuarioConCorreoInexistente(){
        assertThrows(ElementosNoEncontrados.class, () -> usuarioService.obtenerEventosDeUsuario(999));
    }
}
