package sistema.guilhermejr.net.salario_service.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistema.guilhermejr.net.salario_service.api.response.AnoResponse;
import sistema.guilhermejr.net.salario_service.service.AnoService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('SALARIO')")
@RequestMapping("/ano")
public class AnoController {

    private final AnoService anoService;

    @GetMapping("")
    public ResponseEntity<List<AnoResponse>> retornar() {

        log.info("Recuperando lista de anos");
        List<AnoResponse> anoResponse = anoService.retornar();
        return ResponseEntity.status(HttpStatus.OK).body(anoResponse);

    }

}
