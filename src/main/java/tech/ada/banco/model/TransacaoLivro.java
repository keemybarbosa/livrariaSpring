package tech.ada.banco.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TRANSACAO_LIVRO")
public class TransacaoLivro {
    @Id
    @SequenceGenerator(name = "transacaoLivroSequenceGenerator", sequenceName = "TRANSACALIVRO_SQ", initialValue = 1)
    @GeneratedValue(generator = "transacaoLivroSequenceGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRANSACAO_ID")
    private Transacao transacao;

    @ManyToOne
    @JoinColumn(name = "LIVRO_ID")
    private Livro livro;

    // getters e setters
}
