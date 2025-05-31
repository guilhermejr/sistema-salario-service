package sistema.guilhermejr.net.salario_service.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FolhaResponse extends FolhaListagemResponse {

    private List<ItemResponse> items;

}
