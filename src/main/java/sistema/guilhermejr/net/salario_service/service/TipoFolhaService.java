package sistema.guilhermejr.net.salario_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sistema.guilhermejr.net.salario_service.api.mapper.TipoFolhaMapper;
import sistema.guilhermejr.net.salario_service.api.request.TipoFolhaRequest;
import sistema.guilhermejr.net.salario_service.api.response.TipoFolhaResponse;
import sistema.guilhermejr.net.salario_service.config.security.AuthenticationCurrentUserService;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoFolha;
import sistema.guilhermejr.net.salario_service.domain.repository.TipoFolhaRepository;
import sistema.guilhermejr.net.salario_service.exception.ExceptionNotFound;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class TipoFolhaService {

    private final TipoFolhaRepository tipoFolhaRepository;
    private final TipoFolhaMapper tipoFolhaMapper;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public List<TipoFolhaResponse> retornar() {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        List<TipoFolha> tipoFolhas = tipoFolhaRepository.findAllByUsuarioOrderByDescricaoAsc(usuario);

        return tipoFolhaMapper.mapList(tipoFolhas);

    }

    public TipoFolhaResponse retornaUm(Long id) {

        TipoFolha tipoFolha = tipoFolhaExiste(id);
        if (tipoFolha != null) {
            return tipoFolhaMapper.mapObject(tipoFolha);
        } else {
            log.error("Tipo Folha não retornada: {}", id);
            throw new ExceptionNotFound("Não pode retornar tipo de folha. Id não encontrado: " + id);
        }

    }

    private TipoFolha tipoFolhaExiste(Long id) {

        TipoFolha tipoFolha = tipoFolhaRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ExceptionNotFound("Tipo folha não encontrado: " + id));
        return tipoFolha;

    }

    @Transactional
    public TipoFolhaResponse inserir(TipoFolhaRequest tipoFolhaRequest) {

        return salvar(tipoFolhaRequest, null);

    }

    private TipoFolhaResponse salvar(TipoFolhaRequest tipoFolhaRequest, Long id) {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        TipoFolha tipoFolha = tipoFolhaMapper.mapObject(tipoFolhaRequest);

        if (id != null) {
            tipoFolha.setId(id);
        }

        tipoFolha.setUsuario(usuario);

        TipoFolha tipoFolhaSave = tipoFolhaRepository.save(tipoFolha);
        return tipoFolhaMapper.mapObject(tipoFolhaSave);

    }
}
