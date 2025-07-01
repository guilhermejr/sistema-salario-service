package sistema.guilhermejr.net.salario_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.guilhermejr.net.salario_service.domain.entity.Ano;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnoRepository extends JpaRepository<Ano, Long> {

    List<Ano> findAllByUsuarioOrderByAnoDesc(UUID usuario);

    Optional<Ano> findByAnoAndUsuario(Integer ano, UUID usuario);

}
