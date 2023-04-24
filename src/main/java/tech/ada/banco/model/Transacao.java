package tech.ada.banco.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "TRANSACAO")
public class Transacao {
    @Id
    @SequenceGenerator(name = "transacaoSequenceGenerator", sequenceName = "TRANSACAO_SQ", initialValue = 1)
    @GeneratedValue(generator = "transacaoSequenceGenerator", strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMPRADOR_ID")
    private Pessoa comprador;

    @OneToMany(mappedBy = "transacao")
    private List<TransacaoLivro> transacaoLivros = new ArrayList<>();

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (TransacaoLivro tl : transacaoLivros) {
            total = total.add(tl.getLivro().getPreco());
        }
        return total;
    }

}
