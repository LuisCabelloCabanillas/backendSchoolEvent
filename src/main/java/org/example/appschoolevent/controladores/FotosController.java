package org.example.appschoolevent.controladores;

import lombok.AllArgsConstructor;
import org.example.appschoolevent.DTO.FotosDTO;
import org.example.appschoolevent.servicios.FotosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventos")
@AllArgsConstructor
public class FotosController {

    private final FotosService fotosService;

    @PostMapping("/{id}/galeria")
    public ResponseEntity<?> subirFoto(
            @PathVariable Integer id,
            @RequestParam String url) {
        {
            FotosDTO fotoGuardada = fotosService.guardarFoto(id, url);
            return ResponseEntity.ok(fotoGuardada);
        }
    }

}
