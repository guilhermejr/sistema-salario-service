package sistema.guilhermejr.net.salario_service.api.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemResponse {

    private Long id;
    private String valor;
    private String unidade;
    private LocalDateTime criado;
    private LocalDateTime atualizado;
    private TipoItemResponse tipoItem;
    private UUID usuario;

}
