package sistema.guilhermejr.net.salario_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sistema.guilhermejr.net.salario_service.api.mapper.FolhaMapper;
import sistema.guilhermejr.net.salario_service.api.request.FolhaRequest;
import sistema.guilhermejr.net.salario_service.api.response.FolhaListagemResponse;
import sistema.guilhermejr.net.salario_service.api.response.FolhaResponse;
import sistema.guilhermejr.net.salario_service.config.security.AuthenticationCurrentUserService;
import sistema.guilhermejr.net.salario_service.domain.entity.*;
import sistema.guilhermejr.net.salario_service.domain.repository.*;
import sistema.guilhermejr.net.salario_service.exception.ExceptionNotFound;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class FolhaService {

    private final FolhaRepository folhaRepository;
    private final ItemRepository itemRepository;
    private final TipoFolhaRepository tipoFolhaRepository;
    private final TipoItemRepository tipoItemRepository;
    private final AnoRepository anoRepository;
    private final FolhaMapper folhaMapper;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public List<FolhaListagemResponse> retornar() {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        List<Folha> folhas = folhaRepository.findAllByUsuarioOrderByDataPagamentoDesc(usuario);

        return folhaMapper.mapList(folhas);

    }

    public List<FolhaListagemResponse> retornarAno(Long ano) {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        List<Folha> folhas = folhaRepository.retornaAno(usuario, ano);

        return folhaMapper.mapList(folhas);

    }

    public FolhaResponse retornaUm(Long id) {
        return folhaMapper.mapObject(folhaRepository.retornaUm(id));
    }

    @Transactional
    public FolhaResponse inserir(FolhaRequest folhaRequest) {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();

        Folha folha = folhaMapper.mapObject(folhaRequest);
        folha.setId(null);
        folha.setUsuario(usuario);

        List<Item> itens = folha.getItems();

        // --- Calcula valores ---
        BigDecimal proventos = BigDecimal.ZERO;
        BigDecimal descontos = BigDecimal.ZERO;

        for (Item item: itens) {

            TipoItem tipoItem = tipoItemRepository.findById(item.getTipoItem().getId()).orElseThrow(() -> new ExceptionNotFound("Tipo item não encontrado: " + item.getTipoItem().getId()));

            if (tipoItem.getTipo() == 'P') {
                proventos = proventos.add(item.getValor());
            }

            if (tipoItem.getTipo() == 'D') {
                descontos = descontos.add(item.getValor());
            }
        }

        BigDecimal liquido = proventos.subtract(descontos);

        folha.setTotalDescontos(descontos);
        folha.setTotalProventos(proventos);
        folha.setTotalLiquido(liquido);

        // --- Tipo Folha ---
        TipoFolha tipoFolha = tipoFolhaRepository.findById(folha.getTipoFolha().getId()).orElseThrow(() -> new ExceptionNotFound("Tipo folha não encontrado: "+ folha.getTipoFolha().getId()));
        folha.setTipoFolha(tipoFolha);

        // --- Salva folha ---
        folhaRepository.save(folha);

        // --- Items ---
        List<Item> item = new ArrayList<>();
        itens.forEach(i -> {
            i.setFolha(folha);
            i.setUsuario(usuario);
            itemRepository.save(i);
            item.add(i);
        });

        // --- Verifica se precisa criar registro com ano ---
        Integer ano = Integer.parseInt(folhaRequest.getDataPagamento().substring(6));
        if (anoRepository.findByAnoAndUsuario(ano, usuario).isEmpty()) {
            Ano anoGravar = Ano.builder().ano(ano).usuario(usuario).build();
            anoRepository.save(anoGravar);

            log.info("Criado novo ano: {}", ano);
        }

        return folhaMapper.mapObject(folha);

    }

}
