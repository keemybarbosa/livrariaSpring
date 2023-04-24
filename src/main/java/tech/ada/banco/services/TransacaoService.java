package tech.ada.banco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.banco.model.*;
import tech.ada.banco.repository.*;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private TransacaoLivroRepository transacaoLivroRepository;

    @Transactional
    public void realizarTransacao(Long compradorId, List<Long> livroIds) {
        // Busca o comprador pelo ID
        Pessoa comprador = pessoaRepository.findById(compradorId).orElseThrow(() ->
                new RuntimeException("Comprador não encontrado"));

        // Cria a transação
        Transacao transacao = new Transacao();
        transacao.setComprador(comprador);

        // Adiciona os livros na transação
        for (Long livroId : livroIds) {
            Livro livro = livroRepository.findById(livroId).orElseThrow(() ->
                    new RuntimeException("Livro não encontrado"));

            TransacaoLivro transacaoLivro = new TransacaoLivro();
            transacaoLivro.setLivro(livro);
            transacaoLivro.setTransacao(transacao);

            transacao.getTransacaoLivros().add(transacaoLivro);
        }

        // Salva a transação e as transações de livros
        transacaoRepository.save(transacao);
        transacaoLivroRepository.saveAll(transacao.getTransacaoLivros());

        // Atualiza o saldo do comprador
        comprador.setSaldo(comprador.getSaldo().subtract(transacao.getTotal()));

        pessoaRepository.save(comprador);
    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }
}