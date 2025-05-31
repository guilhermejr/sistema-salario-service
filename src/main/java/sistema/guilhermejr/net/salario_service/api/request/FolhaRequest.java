package sistema.guilhermejr.net.salario_service.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sistema.guilhermejr.net.salario_service.api.request.validation.constraints.DataBrasil;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FolhaRequest {

    @NotBlank
    @DataBrasil
    private String dataPagamento;

    @NotNull
    private Long tipoFolhaId;

    @NotNull
    private List<ItemRequest> itens;

}
