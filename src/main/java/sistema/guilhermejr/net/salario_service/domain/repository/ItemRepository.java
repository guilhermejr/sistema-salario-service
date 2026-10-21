package sistema.guilhermejr.net.salario_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.guilhermejr.net.salario_service.domain.entity.Item;
import sistema.guilhermejr.net.salario_service.domain.entity.TipoItem;

public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByTipoItem(TipoItem tipoItem);

}
