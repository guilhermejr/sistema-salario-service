package sistema.guilhermejr.net.salario_service.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sistema.guilhermejr.net.salario_service.api.request.validation.constraints.ValorMonetario;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemRequest {

    @NotBlank
    @ValorMonetario
    private String valor;

    @NotNull
    private Long tipoItemId;

    @ValorMonetario
    private String unidade;

}
