package sistema.guilhermejr.net.salario_service.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sistema.guilhermejr.net.salario_service.api.request.validation.constraints.TipoItemTipo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TipoItemRequest {

    @NotBlank
    private String descricao;

    private String dica;

    @TipoItemTipo
    private Character tipo;

}
