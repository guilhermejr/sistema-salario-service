package sistema.guilhermejr.net.salario_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoItem;

import java.util.List;
import java.util.UUID;

public interface TipoItemRepository extends JpaRepository<TipoItem, Long> {
    List<TipoItem> findAllByUsuarioOrderByDescricaoAsc(UUID usuario);

    List<TipoItem> findAllByUsuarioAndTipoAndAtivoTrueOrderByDescricaoAsc(UUID usuario, Character p);
}
