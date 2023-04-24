package tech.ada.banco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.banco.exceptions.ResourceNotFoundException;
import tech.ada.banco.model.XXPessoa;
import tech.ada.banco.repository.XXPessoaRepository;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final XXPessoaRepository repository;

    @GetMapping("{id}")
    public XXPessoa getPessoa(@PathVariable int id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    public List<XXPessoa> getPessoas() {
        return repository.findAll();
    }

    @PostMapping
    public XXPessoa createPessoa(@RequestBody XXPessoa pessoa) {
        return repository.save(pessoa);
    }

    @PutMapping
    public XXPessoa atualizaPessoa(@RequestBody XXPessoa pessoa) {
        return repository.save(pessoa);
    }
}
