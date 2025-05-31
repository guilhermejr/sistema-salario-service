package sistema.guilhermejr.net.salario_service.api.mapper;

import org.modelmapper.Converter;
import org.springframework.stereotype.Component;
import sistema.guilhermejr.net.salario_service.api.request.FolhaRequest;
import sistema.guilhermejr.net.salario_service.api.request.ItemRequest;
import sistema.guilhermejr.net.salario_service.api.response.FolhaListagemResponse;
import sistema.guilhermejr.net.salario_service.api.response.FolhaResponse;
import sistema.guilhermejr.net.salario_service.api.response.ItemResponse;
import sistema.guilhermejr.net.salario_service.api.response.TipoItemResponse;
import sistema.guilhermejr.net.salario_service.config.ModelMapperConfig;
import sistema.guilhermejr.net.salario_service.domain.entity.Folha;
import sistema.guilhermejr.net.salario_service.domain.entity.Item;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoItem;
import sistema.guilhermejr.net.salario_service.util.ConverteStringUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class FolhaMapper extends ModelMapperConfig {

    public FolhaMapper(ConverteStringUtil converteStringUtil) {

        Converter<List<ItemRequest>, List<Item>> itemConverterRequest = ctx -> {
          List<Item> itens = new ArrayList<>();
          ctx.getSource().forEach(item -> {
              itens.add(Item.builder().valor(converteStringUtil.toBigDecimal(item.getValor())).tipoItem(TipoItem.builder().id(item.getTipoItemId()).build()).build());
          });

          return itens;

        };

        final boolean[] ordena = {true};

        Converter<List<Item>, List<ItemResponse>> itemConvertResponse = ctx -> {
            List<ItemResponse> itens = new ArrayList<>();
            ctx.getSource().forEach(item -> {

                if (ordena[0] && item.getTipoItem().getTipo() == null) {
                    ordena[0] = false;
                }

                TipoItemResponse tipoItemResponse = TipoItemResponse.builder()
                        .id(item.getTipoItem().getId())
                        .descricao(item.getTipoItem().getDescricao())
                        .dica(item.getTipoItem().getDica())
                        .tipo(item.getTipoItem().getTipo())
                        .build();
                ItemResponse itemResponse = ItemResponse.builder()
                        .id(item.getId())
                        .valor(converteStringUtil.toStringBigDecimal(item.getValor()))
                        .tipoItem(tipoItemResponse)
                        .build();
                itens.add(itemResponse);
            });

            if (ordena[0]) {
                itens.sort(Comparator.comparing((ItemResponse i) -> i.getTipoItem().getTipo()).reversed());
            }

            return itens;
        };

        Converter<String, LocalDate> converterData = ctx -> converteStringUtil.toLocalDate(ctx.getSource());

        Converter<LocalDate, String> converterData2String = ctx -> converteStringUtil.toStringData(ctx.getSource());

        Converter<BigDecimal, String> converterBigDecimal2String = ctx -> converteStringUtil.toStringBigDecimal(ctx.getSource());

        this.modelMapper.createTypeMap(FolhaRequest.class, Folha.class)
                .addMappings(mapper -> mapper.using(itemConverterRequest).map(FolhaRequest::getItens, Folha::setItems))
                .addMappings(mapper -> mapper.using(converterData).map(FolhaRequest::getDataPagamento, Folha::setDataPagamento));

        this.modelMapper.createTypeMap(Folha.class, FolhaResponse.class)
                .addMappings(mapper -> mapper.using(converterData2String).map(Folha::getDataPagamento, FolhaResponse::setDataPagamento))
                .addMappings(mapper -> mapper.using(converterBigDecimal2String).map(Folha::getTotalProventos, FolhaResponse::setTotalProventos))
                .addMappings(mapper -> mapper.using(converterBigDecimal2String).map(Folha::getTotalDescontos, FolhaResponse::setTotalDescontos))
                .addMappings(mapper -> mapper.using(converterBigDecimal2String).map(Folha::getTotalLiquido, FolhaResponse::setTotalLiquido))
                .addMappings(mapper -> mapper.using(itemConvertResponse).map(Folha::getItems, FolhaResponse::setItems));

        this.modelMapper.createTypeMap(Folha.class, FolhaListagemResponse.class)
                .addMappings(mapper -> mapper.using(converterData2String).map(Folha::getDataPagamento, FolhaListagemResponse::setDataPagamento))
                .addMappings(mapper -> mapper.using(converterBigDecimal2String).map(Folha::getTotalProventos, FolhaListagemResponse::setTotalProventos))
                .addMappings(mapper -> mapper.using(converterBigDecimal2String).map(Folha::getTotalDescontos, FolhaListagemResponse::setTotalDescontos))
                .addMappings(mapper -> mapper.using(converterBigDecimal2String).map(Folha::getTotalLiquido, FolhaListagemResponse::setTotalLiquido));

    }

    public FolhaResponse mapObject(Folha folha) {
        return this.mapObject(folha, FolhaResponse.class);
    }

    public Folha mapObject(FolhaRequest folhaRequest) {
        return this.mapObject(folhaRequest, Folha.class);
    }

    public List<FolhaListagemResponse> mapList(List<Folha> folhas) {
        return this.mapList(folhas, FolhaListagemResponse.class);
    }
}
