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
public class TipoItemResponse {

    private Long id;
    private String descricao;
    private String dica;
    private Character tipo;
    private Boolean ativo;
    private LocalDateTime criado;
    private LocalDateTime atualizado;
    private UUID usuario;

}
