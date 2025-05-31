package sistema.guilhermejr.net.salario_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoFolha;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoFolhaRepository extends JpaRepository<TipoFolha, Long> {

    List<TipoFolha> findAllByUsuarioOrderByDescricaoAsc(UUID usuario);

    Optional<TipoFolha> findByIdAndAtivoTrue(Long id);

}
