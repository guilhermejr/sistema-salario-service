package sistema.guilhermejr.net.salario_service.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "folhas")
public class Folha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;

    @Column(nullable = false)
    private BigDecimal totalProventos = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalDescontos = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalLiquido = BigDecimal.ZERO;

    @ManyToOne
    private TipoFolha tipoFolha;

    @OneToMany(mappedBy = "folha")
    @JsonManagedReference
    private List<Item> items;

    @Column(nullable = false)
    private UUID usuario;

}
