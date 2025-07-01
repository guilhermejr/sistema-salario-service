package sistema.guilhermejr.net.salario_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sistema.guilhermejr.net.salario_service.api.mapper.AnoMapper;
import sistema.guilhermejr.net.salario_service.api.response.AnoResponse;
import sistema.guilhermejr.net.salario_service.config.security.AuthenticationCurrentUserService;
import sistema.guilhermejr.net.salario_service.domain.entity.Ano;
import sistema.guilhermejr.net.salario_service.domain.repository.AnoRepository;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class AnoService {

    private final AnoRepository anoRepository;
    private final AnoMapper anoMapper;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public List<AnoResponse> retornar() {

        UUID usuario = authenticationCurrentUserService.getCurrentUser().getId();
        List<Ano> ano = anoRepository.findAllByUsuarioOrderByAnoDesc(usuario);

        return anoMapper.mapList(ano);

    }
}
