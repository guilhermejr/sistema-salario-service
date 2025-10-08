package sistema.guilhermejr.net.salario_service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistema.guilhermejr.net.salario_service.api.request.TipoItemRequest;
import sistema.guilhermejr.net.salario_service.api.response.TipoItemResponse;
import sistema.guilhermejr.net.salario_service.service.TipoItemService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('SALARIO')")
@RequestMapping("/tipo-item")
public class TipoItemController {

    private final TipoItemService tipoItemService;

    @GetMapping("/P")
    public ResponseEntity<List<TipoItemResponse>> retornarP() {

        log.info("Retornando tipo de item Proventos");
        List<TipoItemResponse> tipoItemResponseList = tipoItemService.retornarP();
        return ResponseEntity.status(HttpStatus.OK).body(tipoItemResponseList);

    }

    @GetMapping("/D")
    public ResponseEntity<List<TipoItemResponse>> retornarD() {

        log.info("Retornando tipo de item Descontos");
        List<TipoItemResponse> tipoItemResponseList = tipoItemService.retornarD();
        return ResponseEntity.status(HttpStatus.OK).body(tipoItemResponseList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoItemResponse> retornarUm(@PathVariable Long id) {

        log.info("Recuperando um tipo de item: {}", id);
        TipoItemResponse tipoItemResponse = tipoItemService.retornaUm(id);
        return ResponseEntity.status(HttpStatus.OK).body(tipoItemResponse);

    }

    @PostMapping
    public ResponseEntity<TipoItemResponse> inserir(@Valid @RequestBody TipoItemRequest tipoItemRequest) {

        log.info("Inserindo tipo de item");
        TipoItemResponse tipoItemResponse = tipoItemService.inserir(tipoItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoItemResponse);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoItemResponse> atualizar(@PathVariable Long id, @Valid @RequestBody TipoItemRequest tipoItemRequest) {

        log.info("Atualizando tipo de item: {}", id);
        TipoItemResponse tipoItemResponse = tipoItemService.atualizar(id, tipoItemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(tipoItemResponse);

    }
}
