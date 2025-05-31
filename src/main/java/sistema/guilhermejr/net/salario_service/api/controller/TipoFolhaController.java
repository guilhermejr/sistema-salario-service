package sistema.guilhermejr.net.salario_service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistema.guilhermejr.net.salario_service.api.request.TipoFolhaRequest;
import sistema.guilhermejr.net.salario_service.api.response.TipoFolhaResponse;
import sistema.guilhermejr.net.salario_service.service.TipoFolhaService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('SALARIO')")
@RequestMapping("/tipo-folha")
public class TipoFolhaController {

    private final TipoFolhaService tipoFolhaService;

    @GetMapping
    public ResponseEntity<List<TipoFolhaResponse>> retornar() {

        log.info("Retornando tipo de folhas");
        List<TipoFolhaResponse> tipoFolhaResponseList = tipoFolhaService.retornar();
        return ResponseEntity.status(HttpStatus.OK).body(tipoFolhaResponseList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoFolhaResponse> retornarUm(@PathVariable Long id) {

        log.info("Recuperando um tipo de folha: {}", id);
        TipoFolhaResponse tipoFolhaResponse = tipoFolhaService.retornaUm(id);
        return ResponseEntity.status(HttpStatus.OK).body(tipoFolhaResponse);

    }

    @PostMapping
    public ResponseEntity<TipoFolhaResponse> inserir(@Valid @RequestBody TipoFolhaRequest tipoFolhaRequest) {

        log.info("Inserindo tipo de folha");
        TipoFolhaResponse tipoFolhaResponse = tipoFolhaService.inserir(tipoFolhaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoFolhaResponse);

    }

}
