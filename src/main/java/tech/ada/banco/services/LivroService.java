package tech.ada.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.banco.model.Livro;
import tech.ada.banco.repository.LivroRepository;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro criarLivro(String nome, String edicao, String autor, int quantidade) {
        Livro livro = new Livro(nome, quantidade);
        livro.setEdicao(edicao);
        livro.setAutor(autor);
        return livroRepository.save(livro);
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    public void excluirLivro(Long id) {
        Livro livro = buscarLivroPorId(id);
        if (livro != null) {
            livroRepository.delete(livro);
        }
    }

    public Livro atualizarLivro(Long id, String nome, String edicao, String autor, int quantidade) {
        Livro livro = buscarLivroPorId(id);
        if (livro != null) {
            livro.setNome(nome);
            livro.setEdicao(edicao);
            livro.setAutor(autor);
            livro.setQuantidade(quantidade);
            return livroRepository.save(livro);
        }
        return null;
    }
}