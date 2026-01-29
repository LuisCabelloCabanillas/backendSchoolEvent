package org.example.appschoolevent.servicios;


import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.DTO.FotosDTO;
import org.example.appschoolevent.DTO.InscripcionDTO;
import org.example.appschoolevent.mappers.EventoMapper;
import org.example.appschoolevent.mappers.FotosMapper;
import org.example.appschoolevent.mappers.InscripcionMapper;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.modelo.Fotos;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.example.appschoolevent.repositorio.FotosRepository;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EventoServiceIntegrationTest {

    @InjectMocks
    private EventoService eventoService;

    @InjectMocks
    private InscripcionService inscripcionService;

    @InjectMocks
    private FotosService fotosService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private EventoMapper mapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private InscripcionMapper inscripcionMapper;

    @Mock
    private FotosRepository fotosRepository;

    @Mock
    private FotosMapper fotosMapper;

    @Mock
    private EventoService eventoServiceMock;


    @Test
    @DisplayName("Test de Integración -> BuscarPorID()")
    public void buscarPorIdIntegrationTest(){

        //GIVEN
        Mockito.when(this.eventoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Evento()));
        Mockito.when(this.mapper.toDTO(Mockito.any(Evento.class))).thenReturn(new EventoDTO());

        //WHEN
        this.eventoService.obtenerEventoPorId(1);


        //THEN
        Mockito.verify(this.eventoRepository).findById(Mockito.anyInt());
        Mockito.verify(this.mapper).toDTO(Mockito.any());


    }

    @Test
    @DisplayName("Test de Integración -> CrearEvento()")
    public void crearEventoIntegrationTest(){

        //GIVEN
        Mockito.when(this.eventoRepository.save(Mockito.any(Evento.class))).thenReturn(new Evento());
        Mockito.when(this.mapper.toDTO(Mockito.any(Evento.class))).thenReturn(new EventoDTO());

        //WHEN
        this.eventoService.crearEvento(new EventoDTO());

        //THEN
        Mockito.verify(this.eventoRepository).save(Mockito.any());
        Mockito.verify(this.mapper).toDTO(Mockito.any());
    }

    @Test
    @DisplayName("Test de Integración -> FiltrarEventos()")
    public void filtrarEventosIntegrationTest(){

        //GIVEN
        Mockito.when(this.eventoRepository.filtrarEventos(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new Evento()));
        Mockito.when(this.mapper.toDTO(Mockito.any(Evento.class)))
                .thenReturn(new EventoDTO());

        //WHEN
        this.eventoService.filtarEventos(null, null);

        //THEN
        Mockito.verify(this.eventoRepository).filtrarEventos(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Test de Integración -> ModificarEventos()")
    public void actualizarEventoIntegrationTest(){
        Evento eventoExistente = new Evento();

        Mockito.when(this.eventoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(eventoExistente));
        Mockito.when(this.eventoRepository.save(Mockito.any(Evento.class)))
                .thenReturn(eventoExistente);
        Mockito.when(this.mapper.toDTO(Mockito.any(Evento.class)))
                .thenReturn(new EventoDTO());

        EventoDTO dto = new EventoDTO();
        dto.setNombre("Evento prueba");
        dto.setFecha("2026-01-25");
        dto.setLugar("Aula 1");
        dto.setConsiste("Descripción de prueba");
        dto.setCategoria("Cultural");

        eventoService.actualizarEvento(1, dto);

        Mockito.verify(this.eventoRepository).findById(Mockito.anyInt());
        Mockito.verify(this.eventoRepository).save(Mockito.any());
        Mockito.verify(this.mapper).toDTO(Mockito.any());
    }

    @Test
    @DisplayName("Test de Integración -> InscribirUsuario()")
    public void crearEvento(){

        Mockito.when(this.eventoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new Evento()));

        Mockito.when(this.usuarioRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.ofNullable(new Usuario()));

        Mockito.when(inscripcionRepository.existsByUsuarioIdAndEventoId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(false);

        Mockito.when(inscripcionMapper.toDTO(Mockito.any(Inscripcion.class)))
                .thenReturn(new InscripcionDTO());

        inscripcionService.inscribirUsuario(1, 1);

        Mockito.verify(this.eventoRepository).findById(Mockito.anyInt());
        Mockito.verify(this.usuarioRepository).findById(Mockito.anyInt());
        Mockito.verify(this.inscripcionRepository).save(Mockito.any());
        Mockito.verify(this.inscripcionMapper).toDTO(Mockito.any());
    }

    @Test
    @DisplayName("Test de Integración -> guardarFoto()")
    public void guardarFotoIntegrationTest(){

        // GIVEN
        Evento evento = new Evento();
        Mockito.when(this.eventoServiceMock.obtenerEventoPorIdEntidad(Mockito.anyInt()))
                .thenReturn(evento);

        Fotos foto = new Fotos();
        Mockito.when(this.fotosRepository.save(Mockito.any()))
                .thenReturn(foto);

        Mockito.when(this.fotosMapper.toDTO(Mockito.any(Fotos.class)))
                .thenReturn(new FotosDTO());

        // WHEN
        fotosService.guardarFoto(1, "ruta/de/prueba.jpg");

        // THEN
        Mockito.verify(eventoServiceMock).obtenerEventoPorIdEntidad(Mockito.anyInt());
        Mockito.verify(fotosRepository).save(Mockito.any());
        Mockito.verify(fotosMapper).toDTO(Mockito.any());
    }
}
