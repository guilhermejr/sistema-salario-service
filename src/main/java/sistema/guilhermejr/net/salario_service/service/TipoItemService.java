package sistema.guilhermejr.net.salario_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sistema.guilhermejr.net.salario_service.api.mapper.TipoItemMapper;
import sistema.guilhermejr.net.salario_service.api.request.TipoItemRequest;
import sistema.guilhermejr.net.salario_service.api.response.TipoItemResponse;
import sistema.guilhermejr.net.salario_service.config.security.AuthenticationCurrentUserService;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoItem;
import sistema.guilhermejr.net.salario_service.domain.repository.TipoItemRepository;
import sistema.guilhermejr.net.salario_service.exception.ExceptionNotFound;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class TipoItemService {

    private final TipoItemRepository tipoItemRepository;
    private final TipoItemMapper tipoItemMapper;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public List<TipoItemResponse> retornarP() {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        List<TipoItem> tipoItems = tipoItemRepository.findAllByUsuarioAndTipoAndAtivoTrueOrderByDescricaoAsc(usuario, 'P');

        return tipoItemMapper.mapList(tipoItems);

    }

    public List<TipoItemResponse> retornarD() {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        List<TipoItem> tipoItems = tipoItemRepository.findAllByUsuarioAndTipoAndAtivoTrueOrderByDescricaoAsc(usuario, 'D');

        return tipoItemMapper.mapList(tipoItems);

    }

    public TipoItemResponse retornaUm(Long id) {

        TipoItem tipoItem = tipoItemExiste(id);
        if (tipoItem != null) {
            return tipoItemMapper.mapObject(tipoItem);
        } else {
            log.error("Tipo Item não retornado: {}", id);
            throw new ExceptionNotFound("Não pode retornar tipo de item. Id não encontrado: " + id);
        }

    }

    @Transactional
    public TipoItemResponse inserir(TipoItemRequest tipoItemRequest) {

        return salvar(tipoItemRequest, null);

    }

    private TipoItem tipoItemExiste(Long id) {

        TipoItem tipoItem = tipoItemRepository.findById(id).orElseThrow(() -> new ExceptionNotFound("Tipo item não encontrado: " +  id));
        return tipoItem;

    }

    private TipoItemResponse salvar(TipoItemRequest tipoItemRequest, Long id) {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        TipoItem tipoItem = tipoItemMapper.mapObject(tipoItemRequest);

        if (id != null) {
            tipoItem.setId(id);
        }

        tipoItem.setUsuario(usuario);

        TipoItem tipoItemSave = tipoItemRepository.save(tipoItem);
        return tipoItemMapper.mapObject(tipoItemSave);

    }

}
