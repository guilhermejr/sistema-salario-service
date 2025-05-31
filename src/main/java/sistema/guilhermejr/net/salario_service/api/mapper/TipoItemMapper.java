package sistema.guilhermejr.net.salario_service.api.mapper;

import org.springframework.stereotype.Component;
import sistema.guilhermejr.net.salario_service.api.request.TipoItemRequest;
import sistema.guilhermejr.net.salario_service.api.response.TipoItemResponse;
import sistema.guilhermejr.net.salario_service.config.ModelMapperConfig;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoItem;

import java.util.List;

@Component
public class TipoItemMapper extends ModelMapperConfig {

    public TipoItemResponse mapObject(TipoItem tipoItem) {
        return this.mapObject(tipoItem, TipoItemResponse.class);
    }

    public TipoItem mapObject(TipoItemRequest tipoItemRequest) {
        return this.mapObject(tipoItemRequest, TipoItem.class);
    }

    public List<TipoItemResponse> mapList(List<TipoItem> tipoItems) {
        return this.mapList(tipoItems, TipoItemResponse.class);
    }

}
