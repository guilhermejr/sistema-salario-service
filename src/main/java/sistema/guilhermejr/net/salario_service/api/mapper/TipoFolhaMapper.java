package sistema.guilhermejr.net.salario_service.api.mapper;

import org.springframework.stereotype.Component;
import sistema.guilhermejr.net.salario_service.api.request.TipoFolhaRequest;
import sistema.guilhermejr.net.salario_service.api.response.TipoFolhaResponse;
import sistema.guilhermejr.net.salario_service.config.ModelMapperConfig;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoFolha;

import java.util.List;

@Component
public class TipoFolhaMapper extends ModelMapperConfig {

    public TipoFolhaResponse mapObject(TipoFolha tipoFolha) {
        return this.mapObject(tipoFolha, TipoFolhaResponse.class);
    }

    public TipoFolha mapObject(TipoFolhaRequest tipoFolhaRequest) {
        return this.mapObject(tipoFolhaRequest, TipoFolha.class);
    }

    public List<TipoFolhaResponse> mapList(List<TipoFolha> tipoFolhas) {
        return this.mapList(tipoFolhas, TipoFolhaResponse.class);
    }

}
