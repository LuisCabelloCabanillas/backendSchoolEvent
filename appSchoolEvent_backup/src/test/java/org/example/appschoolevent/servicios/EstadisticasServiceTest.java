package org.example.appschoolevent.servicios;

import jakarta.transaction.Transactional;
import org.example.appschoolevent.DTO.UsuarioActividadDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.EventoRepository;
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
public class EstadisticasServiceTest {

    @Autowired
    private EventoRepository repositorio;

    @Autowired
    private EstadisticaService estadisticaService;

    @Autowired
    private UsuarioActividadService usuarioActividadService;

    @Autowired
    private org.example.appschoolevent.repositorio.InscripcionRepository inscripcionRepository;
    @Autowired
    private org.example.appschoolevent.repositorio.EventoRepository eventoRepository;
    @Autowired
    private org.example.appschoolevent.repositorio.UsuarioRepository usuarioRepository;
    private Usuario usuario1;
    private Usuario usuario2;
    private Evento evento;


    @BeforeEach
    void setUp() {
        inscripcionRepository.deleteAll();
        eventoRepository.deleteAll();
        usuarioRepository.deleteAll();

        // Usuarios
        usuario1 = new Usuario();
        usuario1.setNombre("Luis");
        usuario1.setApellido("Test");
        usuario1.setCorreo("luis@test.com");
        usuario1.setContrasena("1234");
        usuarioRepository.save(usuario1);

        usuario2 = new Usuario();
        usuario2.setNombre("Pepe");
        usuario2.setApellido("Test");
        usuario2.setCorreo("pepe@test.com");
        usuario2.setContrasena("1234");
        usuarioRepository.save(usuario2);

        // Evento
        evento = new Evento();
        evento.setNombre("Evento 1");
        evento.setFecha(LocalDate.now());
        evento.setInscripciones(new HashSet<>());
        eventoRepository.save(evento);

        // Inscripciones:
        // usuario1 tiene 2
        // usuario2 tiene 1

        Inscripcion i1 = new Inscripcion();
        i1.setUsuario(usuario1);
        i1.setEvento(evento);
        inscripcionRepository.save(i1);

        Inscripcion i2 = new Inscripcion();
        i2.setUsuario(usuario1);
        i2.setEvento(evento);
        inscripcionRepository.save(i2);

        Inscripcion i3 = new Inscripcion();
        i3.setUsuario(usuario2);
        i3.setEvento(evento);
        inscripcionRepository.save(i3);
    }


    @Test
    @DisplayName("Servicio 9 -> Caso Positivo")
    void top5Eventos(){
        for (int i = 1; i <= 5; i++) {
            Evento dto = new Evento();
            dto.setNombre("Evento" + i);
            dto.setRequisitos("Ninguno");
            dto.setConsiste("Charla Motivacional");
            dto.setFecha(LocalDate.parse(LocalDate.now().toString()));
            dto.setCategoria(TipoCategoria.valueOf("Deportivo"));
            dto.setLugar("Clase" + i);
            dto.setInscripciones(new HashSet<>());
            repositorio.save(dto);
        }

        var top = estadisticaService.obtenerEstadisticas();
        assertNotNull(top);
        assertTrue(top.size() <= 5);
    }

    @Test
    @DisplayName("Servicio 9 -> Caso Negativo")
    void top5EventosNegativo(){

        inscripcionRepository.deleteAll();
        usuarioRepository.deleteAll();
        repositorio.deleteAll();

        assertThrows(ElementosNoEncontrados.class, () -> estadisticaService.obtenerEstadisticas());
    }

    @Test
    @DisplayName("Servicio 10 -> Caso Positivo")
    void ObtenerEstadisticas(){

        UsuarioActividadDTO usuario = usuarioActividadService.obtenerUsuarioMasActivo();

        assertNotNull(usuario);
        assertEquals(usuario1.getNombre(), usuario.getNombre());
        assertEquals("Luis", usuario.getNombre());
        assertEquals(2L, usuario.getTotalInscripciones());
        assertEquals(0L, usuario.getTotalComentarios());
        assertEquals(2L, usuario.getTotalGlobal());
    }

    @Test
    @DisplayName("Servicio 10 -> Caso Negativo")
    void ObtenerEstadisticasNegativo(){

        inscripcionRepository.deleteAll();
        usuarioRepository.deleteAll();
        eventoRepository.deleteAll();

        assertThrows(ElementosNoEncontrados.class, () -> usuarioActividadService.obtenerUsuarioMasActivo());
    }

}
