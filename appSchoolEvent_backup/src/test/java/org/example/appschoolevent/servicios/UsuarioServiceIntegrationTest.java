package org.example.appschoolevent.servicios;

import org.example.appschoolevent.DTO.EventoDTO;
import org.example.appschoolevent.DTO.UsuarioDTO;
import org.example.appschoolevent.mappers.EventoMapper;
import org.example.appschoolevent.mappers.UsuarioMapper;
import org.example.appschoolevent.modelo.Evento;
import org.example.appschoolevent.modelo.Inscripcion;
import org.example.appschoolevent.modelo.Usuario;
import org.example.appschoolevent.repositorio.InscripcionRepository;
import org.example.appschoolevent.repositorio.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceIntegrationTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private EventoMapper eventoMapper;




    @Test
    @DisplayName("Test de Integración -> RegistrarUsuario()")
    public void registrarUsuarioIntegrationTest(){

        //GIVEN
        Mockito.when(this.usuarioRepository.save(Mockito.any(Usuario.class)))
                .thenReturn(new Usuario());

        Mockito.when(this.usuarioMapper.toDTO(Mockito.any(Usuario.class)))
                .thenReturn(new UsuarioDTO());
        //WHEN
        usuarioService.registrarUsuario(new UsuarioDTO());

        //THEN
        Mockito.verify(this.usuarioRepository).save(Mockito.any());
        Mockito.verify(this.usuarioMapper).toDTO(Mockito.any());

    }

    @Test
    @DisplayName("Integración -> Obtener eventos de usuario")
    public void obtenerEventosUsuarioIntegrationTest(){

        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setNombre("Luis");
        usuario.setApellido("Cabello");
        usuario.setContrasena("calvos");
        usuario.setFecha_de_nacimiento(LocalDate.of(2006, 12, 13));
        usuario.setCorreo("jnuevocorreo@gmail.com");
        usuario.setTipo(Usuario.TipoUsuario.Admin);

        Mockito.when(usuarioRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(usuario));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(new Evento());
        Mockito.when(inscripcionRepository.findByUsuarioId(Mockito.anyInt()))
                .thenReturn(List.of(inscripcion));

        Mockito.when(eventoMapper.toDTO(Mockito.any(Evento.class)))
                .thenReturn(new EventoDTO());

        // WHEN
        usuarioService.obtenerEventosDeUsuario(1);

        // THEN
        Mockito.verify(usuarioRepository).findById(Mockito.anyInt());
        Mockito.verify(inscripcionRepository).findByUsuarioId(Mockito.anyInt());
        Mockito.verify(eventoMapper).toDTO(Mockito.any(Evento.class));
    }
}
