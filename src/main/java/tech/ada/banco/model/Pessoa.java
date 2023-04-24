package tech.ada.banco.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "PESSOA")
public class Pessoa {

    @Id
    @SequenceGenerator(name = "pessoaSequenceGenerator", sequenceName = "PESSOA_SQ", initialValue = 1)
    @GeneratedValue(generator = "pessoaSequenceGenerator", strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private Long id;

    private String nome;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
    @Column(name = "CPF")
    private String cpf;

    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "SALDO")
    private BigDecimal Saldo;

    public Pessoa(String nome, String cpf) {
        this.cpf = cpf;
        this.nome = nome;
    }

    protected Pessoa() {

    }

    public String toString() {
        return "Nome: " + nome + " telefone: " + telefone + " e cpf: " + cpf;
    }


}
