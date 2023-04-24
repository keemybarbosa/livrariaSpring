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
@Table(name = "LIVRO")
public class Livro {

    @Id
    @SequenceGenerator(name = "livroSequenceGenerator", sequenceName = "LIVRO_SQ", initialValue = 1)
    @GeneratedValue(generator = "livroSequenceGenerator", strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EDICAO")
    private String edicao;

    @Column(name = "AUTOR")
    private String autor;

    @Column(name = "PRECO")
    private BigDecimal preco;

    @Column(name = "QUANTIDADE")
    private int quantidade;

    @OneToMany(mappedBy = "livro")
    private List<TransacaoLivro> transacaoLivros = new ArrayList<>();

    public Livro(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    protected Livro() {

    }

}
