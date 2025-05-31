package sistema.guilhermejr.net.salario_service.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TipoFolhaRequest {

    @NotBlank
    private String descricao;

}
