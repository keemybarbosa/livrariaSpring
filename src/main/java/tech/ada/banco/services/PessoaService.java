package tech.ada.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.banco.exceptions.ResourceNotFoundException;
import tech.ada.banco.model.Pessoa;
import tech.ada.banco.repository.PessoaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa criarPessoa(String nome, String cpf, String email, String telefone, BigDecimal saldo) {
        Pessoa pessoa = new Pessoa(nome, cpf);
        pessoa.setEmail(email);
        pessoa.setTelefone(telefone);
        pessoa.setSaldo(saldo);

        return pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
    }

    public List<Pessoa> buscarTodasPessoas() {
        return pessoaRepository.findAll();
    }

    public void deletarPessoaPorId(Long id) {
        Pessoa pessoa = buscarPessoaPorId(id);
        pessoaRepository.delete(pessoa);
    }

    public Pessoa atualizarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
}