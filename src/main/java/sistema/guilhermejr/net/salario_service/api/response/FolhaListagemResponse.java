package sistema.guilhermejr.net.salario_service.api.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FolhaListagemResponse {

    private Long id;
    private String dataPagamento;
    private String totalProventos;
    private String totalDescontos;
    private String totalLiquido;
    private TipoFolhaResponse tipoFolha;
    private UUID usuario;

}
