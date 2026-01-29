package org.example.appschoolevent.servicios;

import org.example.appschoolevent.DTO.EstadisticaDTO;
import org.example.appschoolevent.DTO.UsuarioActividadDTO;
import org.example.appschoolevent.enums.TipoCategoria;
import org.example.appschoolevent.repositorio.EventoRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EstadisticasServiceIntegrationTest {
    @InjectMocks
    private EstadisticaService estadisticasService;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioActividadService usuarioActividadService;

    @Test
    @DisplayName("Integración -> Top 5 eventos con más asistentes")
    public void top5EventosIntegrationTest(){

        // GIVEN
        EstadisticaDTO estadistica = new EstadisticaDTO(
                1, "Evento Test", "Aula 1", "Requisitos", "Descripción",
                TipoCategoria.Cultural, 10L
        );

        Mockito.when(eventoRepository.obtenerEventosIns(Mockito.any(Pageable.class)))
                .thenReturn(List.of(estadistica));

        // WHEN
        List<EstadisticaDTO> resultados = estadisticasService.obtenerEstadisticas();

        // THEN
        Mockito.verify(eventoRepository).obtenerEventosIns(Mockito.any(Pageable.class));
        assertEquals(1, resultados.size());
        assertEquals("Evento Test", resultados.get(0).getNombre());
    }

    @Test
    @DisplayName("Integración -> Usuario con más actividades")
    public void usuarioMasActivoIntegrationTest() {

        // GIVEN
        UsuarioActividadDTO usuarioMock = Mockito.mock(UsuarioActividadDTO.class);
        Mockito.when(usuarioMock.getNombre()).thenReturn("Luis Cabello");
        Mockito.when(usuarioMock.getTotalGlobal()).thenReturn(5L);

        // Mock del repositorio
        Mockito.when(usuarioRepository.obtenerUsuarioMasActivo(Mockito.any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(List.of(usuarioMock));

        // WHEN
        UsuarioActividadDTO resultado = usuarioActividadService.obtenerUsuarioMasActivo();

        // THEN
        Mockito.verify(usuarioRepository)
                .obtenerUsuarioMasActivo(Mockito.any(org.springframework.data.domain.Pageable.class));

        assertEquals("Luis Cabello", resultado.getNombre());
        assertEquals(5L, resultado.getTotalGlobal());
    }
}
