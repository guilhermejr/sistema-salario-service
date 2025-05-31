package sistema.guilhermejr.net.salario_service.api.mapper;

import org.springframework.stereotype.Component;
import sistema.guilhermejr.net.salario_service.api.response.AnoResponse;
import sistema.guilhermejr.net.salario_service.config.ModelMapperConfig;
import sistema.guilhermejr.net.salario_service.domain.entity.Ano;

import java.util.List;

@Component
public class AnoMapper extends ModelMapperConfig {

    public AnoResponse mapObject(Ano ano) {
        return this.mapObject(ano, AnoResponse.class);
    }

    public List<AnoResponse> mapList(List<Ano> ano) {
        return this.mapList(ano, AnoResponse.class);
    }

}
