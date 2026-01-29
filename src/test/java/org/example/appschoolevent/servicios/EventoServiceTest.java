package org.example.appschoolevent.servicios;


import jakarta.transaction.Transactional;
import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.DTO.FotosDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.exceptions.ElementosNoEncontrados;
import org.example.appschoolevent.exceptions.OperacionNoPermitida;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.example.appschoolevent.repositorio.FotosRepository;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.junit.jupiter.api.*;
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
public class
EventoServiceTest {

    @Autowired
    private EventoService servicio;

    @Autowired
    private EventoRepository repositorio;

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private InscripcionRepository inscripcionRepositorio;

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private FotosService fotosService;

    @Autowired
    private FotosRepository fotosRepositorio;


    private Usuario usuarioPrincipal;
    private Evento eventoPrincipal;

    @BeforeEach
    void cargarDatos(){
        inscripcionRepositorio.deleteAll();
        repositorio.deleteAll();
        usuarioRepositorio.deleteAll();

        // Usuario
        usuarioPrincipal = new Usuario();
        usuarioPrincipal.setNombre("Luis");
        usuarioPrincipal.setApellido("Cabello");
        usuarioPrincipal.setContrasena("pelones");
        usuarioPrincipal.setFecha_de_nacimiento(LocalDate.of(2006,12,13));
        usuarioPrincipal.setCorreo("lcabellocabanillas@safareyes.es");
        usuarioPrincipal.setTipo(Usuario.TipoUsuario.User);
        usuarioRepositorio.save(usuarioPrincipal);

        // Evento
        eventoPrincipal = new Evento();
        eventoPrincipal.setNombre("Evento Test");
        eventoPrincipal.setRequisitos("Ninguno");
        eventoPrincipal.setConsiste("Prueba");
        eventoPrincipal.setFecha(LocalDate.now());
        eventoPrincipal.setCategoria(TipoCategoria.Deportivo);
        eventoPrincipal.setLugar("Aula 1");
        eventoPrincipal.setInscripciones(new HashSet<>());
        repositorio.save(eventoPrincipal);

        // Inscripción inicial

    }

    @Test
    @DisplayName("Servicio 2 -> Caso Positivo")
    void crearEvento(){

        //Given (Previo al test)
        EventoDTO dto = new EventoDTO();
        dto.setNombre("Evento Prueba");
        dto.setRequisitos("Ninguno");
        dto.setConsiste("Charla Motivacional");
        dto.setFecha(LocalDate.now().toString());
        dto.setCategoria(TipoCategoria.Cultural.toString());
        dto.setLugar("Auditorio Principal");


        //Then (Ejecución de la prueba)
        EventoDTO resultado = servicio.crearEvento(dto);


        //When (Comprobaciones)
        assertNotNull(resultado);
        assertEquals("Evento Prueba", resultado.getNombre());

    }


    @Test
    @DisplayName("Servicio 2 -> Caso Negativo")
    void crearEventoNegativo(){

        //Given (Previo al test)

        //Then (Ejecución de la prueba)
        EventoDTO dto = new EventoDTO();

        //When (Comprobaciones)
        assertThrows(RuntimeException.class, () -> servicio.crearEvento(dto) );

    }

    @Test
    @DisplayName("Servicio 3 -> Caso Positivo")
    void filtarEventos(){

        var lista = servicio.filtarEventos(
                LocalDate.now(),
                TipoCategoria.Deportivo
        );

        assertNotNull(lista);
        assertFalse(lista.isEmpty());

    }

    @Test
    @DisplayName("Servicio 3 -> Caso Negativo")
    void filtarEventosNegativo(){

        assertThrows(ElementosNoEncontrados.class,
                () -> servicio.filtarEventos(LocalDate.of(1990,1,1),
                        TipoCategoria.Cultural));


    }

    @Test
    @DisplayName("Servicio 4 -> Caso Positivo")
    void detallesEventos(){

        EventoDTO dto = servicio.obtenerEventoPorId(eventoPrincipal.getId());
        assertNotNull(dto);
        assertEquals(eventoPrincipal.getNombre(), dto.getNombre());
    }

    @Test
    @DisplayName("Servicio 4 -> Caso Negativo")
    void detallesEventosNegativo(){

        assertThrows(ElementosNoEncontrados.class, () -> servicio.filtarEventos(LocalDate.of(2000,1,1), TipoCategoria.Cultural) );
    }

    @Test
    @DisplayName("Servicio 5 -> Caso Positivo")
    void actualizarEvento(){

        EventoDTO dto = new EventoDTO();
        dto.setNombre("Evento Prueba");
        dto.setRequisitos("Ninguno");
        dto.setConsiste("Charla Motivacional");
        dto.setFecha(LocalDate.now().toString());
        dto.setCategoria("Deportivo");

        EventoDTO resultado = servicio.actualizarEvento(eventoPrincipal.getId(), dto);

        assertEquals("Evento Prueba",  resultado.getNombre());

    }

    @Test
    @DisplayName("Servicio 5 -> Caso Negativo")
    void actualizarEventoNegativo(){

        EventoDTO dto = new EventoDTO();
        dto.setNombre("Evento Prueba");
        dto.setRequisitos("Ninguno");
        dto.setConsiste("Charla Motivacional");
        dto.setFecha(LocalDate.now().toString());
        dto.setCategoria("Deportivo");

        assertThrows(ElementosNoEncontrados.class, () -> servicio.actualizarEvento(1, dto));

    }

    @Test
    @DisplayName("Servicio 6 -> Caso Positivo")
    void InscribirUsuario(){

        Usuario usuario = usuarioPrincipal;
        Evento evento = eventoPrincipal;

        assertDoesNotThrow(()->inscripcionService.inscribirUsuario(usuario.getId(), evento.getId()));

        boolean existe = inscripcionRepositorio.existsByUsuarioIdAndEventoId(usuario.getId(), evento.getId());
        assertTrue(existe);

    }

    @Test
    @DisplayName("Servicio 6 -> Caso Negativo")
    void InscribirUsuarioNegativo(){

        Usuario usuario = usuarioPrincipal;
        Evento evento = eventoPrincipal;

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);
        inscripcionRepositorio.save(inscripcion);

        OperacionNoPermitida ex = assertThrows(OperacionNoPermitida.class,
                () -> inscripcionService.inscribirUsuario(usuario.getId(), evento.getId()));

        assertEquals("El usuario ya está inscrito en este evento", ex.getMessage());
    }

    @Test
    @DisplayName("Servicio 7 -> Caso Positivo")
    void subirFoto(){

        String url = "http://foto.com/imagen.jpg";

        FotosDTO dto = fotosService.guardarFoto(eventoPrincipal.getId(), url);

        assertNotNull(dto);
        assertEquals(url, dto.getUrl());
        assertEquals(1, fotosRepositorio.count());



    }

    @Test
    @DisplayName("Servicio 7 -> Caso Negativo")
    void subirFotoNegativo(){

        String url = "   ";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fotosService.guardarFoto(eventoPrincipal.getId(), url));

        assertEquals("La URL de la foto no puede estar vacía", ex.getMessage());

    }
}
