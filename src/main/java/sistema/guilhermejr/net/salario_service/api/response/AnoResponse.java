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
public class AnoResponse {

    private Long id;
    private Integer ano;
    private LocalDateTime criado;
    private LocalDateTime atualizado;
    private UUID usuario;

}
