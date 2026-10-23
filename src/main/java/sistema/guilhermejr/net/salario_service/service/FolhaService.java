package sistema.guilhermejr.net.salario_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sistema.guilhermejr.net.salario_service.api.mapper.FolhaMapper;
import sistema.guilhermejr.net.salario_service.api.request.FolhaRequest;
import sistema.guilhermejr.net.salario_service.api.response.FolhaListagemResponse;
import sistema.guilhermejr.net.salario_service.api.response.FolhaResponse;
import net.guilhermejr.seguranca.jwt.AuthenticationCurrentUserService;
import sistema.guilhermejr.net.salario_service.domain.entity.*;
import sistema.guilhermejr.net.salario_service.domain.repository.*;
import sistema.guilhermejr.net.salario_service.exception.ExceptionNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
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

        Folha folha = folhaExiste(id);
        if (folha != null) {
            return folhaMapper.mapObject(folhaRepository.retornaUm(id));
        } else {
            log.error("Folha não retornada: {}", id);
            throw new ExceptionNotFound("Não pode retornar folha. Id não encontrado: " + id);
        }

    }

    private Folha folhaExiste(Long id) {

        // Folha de outro usuário responde 404, como um id que não existe.
        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        Folha folha = folhaRepository.findByIdAndUsuario(id, usuario).orElseThrow(() -> new ExceptionNotFound("Folha não encontrada: " + id));
        return folha;

    }

    @Transactional
    public FolhaResponse inserir(FolhaRequest folhaRequest) {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();

        Folha folha = folhaMapper.mapObject(folhaRequest);
        folha.setId(null);
        folha.setUsuario(usuario);

        gravar(folha, folha.getItems(), usuario);
        garantirAno(folha.getDataPagamento().getYear(), usuario);

        return folhaMapper.mapObject(folha);

    }

    @Transactional
    public FolhaResponse atualizar(Long id, FolhaRequest folhaRequest) {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();

        Folha folha = folhaExiste(id);
        Integer anoAnterior = folha.getDataPagamento().getYear();
        Folha dados = folhaMapper.mapObject(folhaRequest);

        // Os itens são regravados: saem os antigos e entram os da requisição.
        itemRepository.deleteAll(folha.getItems());

        folha.setDataPagamento(dados.getDataPagamento());
        folha.setTipoFolha(dados.getTipoFolha());

        // Os itens novos só entram na folha depois de gravados: o save de uma folha
        // já existente é um merge, que estragaria a lista com itens ainda sem id.
        gravar(folha, dados.getItems(), usuario);
        folha.setItems(dados.getItems());
        garantirAno(folha.getDataPagamento().getYear(), usuario);

        // A folha mudou de ano: o anterior sai da lista se ficou sem folhas.
        if (!anoAnterior.equals(folha.getDataPagamento().getYear())
                && !folhaRepository.existsByUsuarioAndDataPagamentoBetween(usuario, LocalDate.of(anoAnterior, 1, 1), LocalDate.of(anoAnterior, 12, 31))) {
            anoRepository.findByAnoAndUsuario(anoAnterior, usuario).ifPresent(ano -> {
                anoRepository.delete(ano);
                log.info("Removido ano sem folhas: {}", anoAnterior);
            });
        }

        return folhaMapper.mapObject(folha);

    }

    private void gravar(Folha folha, List<Item> itens, UUID usuario) {

        // --- Calcula valores ---
        BigDecimal proventos = BigDecimal.ZERO;
        BigDecimal descontos = BigDecimal.ZERO;

        for (Item item: itens) {

            // Só os itens e o tipo de folha do próprio usuário.
            TipoItem tipoItem = tipoItemRepository.findByIdAndUsuario(item.getTipoItem().getId(), usuario).orElseThrow(() -> new ExceptionNotFound("Tipo item não encontrado: " + item.getTipoItem().getId()));
            item.setTipoItem(tipoItem);
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
        TipoFolha tipoFolha = tipoFolhaRepository.findByIdAndUsuario(folha.getTipoFolha().getId(), usuario).orElseThrow(() -> new ExceptionNotFound("Tipo folha não encontrado: "+ folha.getTipoFolha().getId()));
        folha.setTipoFolha(tipoFolha);

        // --- Salva folha ---
        folhaRepository.save(folha);

        // --- Items ---
        itens.forEach(i -> {
            i.setFolha(folha);
            i.setUsuario(usuario);
            itemRepository.save(i);
        });

    }

    // --- Verifica se precisa criar registro com ano ---
    private void garantirAno(Integer ano, UUID usuario) {

        if (anoRepository.findByAnoAndUsuario(ano, usuario).isEmpty()) {
            Ano anoGravar = Ano.builder().ano(ano).usuario(usuario).build();
            anoRepository.save(anoGravar);

            log.info("Criado novo ano: {}", ano);
        }

    }

}
