package tech.ada.banco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.banco.exceptions.ResourceNotFoundException;
import tech.ada.banco.model.XXConta;
import tech.ada.banco.model.XXModalidadeConta;
import tech.ada.banco.model.XXPessoa;
import tech.ada.banco.repository.XXContaRepository;

import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final XXContaRepository repository;

    @GetMapping("{conta}")
    public XXConta getConta(@PathVariable int conta) {
        return repository.findContaByNumeroConta(conta).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    public List<XXConta> getContas() {
        return repository.findAll();
    }

    @PostMapping
    public XXConta createConta(@RequestParam XXModalidadeConta modalidade,
                               @RequestBody(required = false) XXPessoa pessoa) {
        return repository.save(new XXConta(modalidade, pessoa));
    }

    @DeleteMapping("{conta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteConta(@PathVariable int conta) {
        repository.deleteById(conta);
    }
}
