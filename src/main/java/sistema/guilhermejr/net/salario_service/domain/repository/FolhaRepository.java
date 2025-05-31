package sistema.guilhermejr.net.salario_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sistema.guilhermejr.net.salario_service.domain.entity.Folha;

import java.util.List;
import java.util.UUID;

public interface FolhaRepository extends JpaRepository<Folha, Long> {

    List<Folha> findAllByUsuarioOrderByDataPagamentoDesc(UUID usuario);

//    @Query( value = "SELECT " +
//            "* " +
//            "FROM " +
//            "folhas AS f " +
//            "INNER JOIN tipo_folhas AS tp " +
//            "ON f.tipo_folha_id = tp.id " +
//            "INNER JOIN itens AS i " +
//            "ON i.folha_id = f.id " +
//            "INNER JOIN tipo_itens AS ti " +
//            "ON ti.id = i.tipo_item_id " +
//            "WHERE " +
//            "f.id = ?1 " +
//            "ORDER BY " +
//            "ti.tipo DESC", nativeQuery = true)
//    List<Object[]> retornaFolha(Long id);

    @Query("SELECT f FROM Folha f JOIN TipoFolha tf ON f.tipoFolha = tf JOIN Item i ON i.folha = f JOIN TipoItem ti ON ti = i.tipoItem WHERE f.id = ?1 ORDER BY ti.tipo ASC")
    Folha retornaUm(Long id);

    @Query("SELECT f FROM Folha f JOIN TipoFolha tf ON f.tipoFolha = tf JOIN Item i ON i.folha = f JOIN TipoItem ti ON ti = i.tipoItem WHERE f.usuario = ?1 AND EXTRACT(YEAR FROM f.dataPagamento) = ?2 ORDER BY ti.tipo ASC")
    List<Folha> retornaAno(UUID usuario, Long ano);
}