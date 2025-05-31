package sistema.guilhermejr.net.salario_service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistema.guilhermejr.net.salario_service.api.request.FolhaRequest;
import sistema.guilhermejr.net.salario_service.api.response.FolhaListagemResponse;
import sistema.guilhermejr.net.salario_service.api.response.FolhaResponse;
import sistema.guilhermejr.net.salario_service.domain.entity.Folha;
import sistema.guilhermejr.net.salario_service.service.FolhaService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('SALARIO')")
@RequestMapping("/folha")
public class FolhaController {

    private final FolhaService folhaService;

    @GetMapping
    public ResponseEntity<List<FolhaListagemResponse>> retornar() {

        log.info("Retornando folhas");
        List<FolhaListagemResponse> tipoFolhaResponseList = folhaService.retornar();
        return ResponseEntity.status(HttpStatus.OK).body(tipoFolhaResponseList);

    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<FolhaListagemResponse>> retornarAno(@PathVariable Long ano) {

        log.info("Retornando folhas por ano: {}", ano);
        List<FolhaListagemResponse> tipoFolhaResponseList = folhaService.retornarAno(ano);
        return ResponseEntity.status(HttpStatus.OK).body(tipoFolhaResponseList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FolhaResponse> retornarUm(@PathVariable Long id) {

        log.info("Recuperando uma folha: {}", id);
        FolhaResponse tipoFolhaResponse = folhaService.retornaUm(id);
        return ResponseEntity.status(HttpStatus.OK).body(tipoFolhaResponse);

    }

    @PostMapping
    public ResponseEntity<FolhaResponse> inserir(@Valid @RequestBody FolhaRequest folhaRequest) {

        log.info("Inserindo folha");
        FolhaResponse folhaResponse = folhaService.inserir(folhaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(folhaResponse);

    }

}
