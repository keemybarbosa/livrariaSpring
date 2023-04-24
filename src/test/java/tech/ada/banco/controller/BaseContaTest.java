package tech.ada.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tech.ada.banco.model.XXConta;
import tech.ada.banco.model.XXModalidadeConta;
import tech.ada.banco.repository.XXContaRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseContaTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected XXContaRepository repository;

    protected XXConta criarConta(BigDecimal saldo) {
        XXConta contaBase = repository.save(new XXConta(XXModalidadeConta.CC, null));
        contaBase.deposito(saldo);
        contaBase = repository.save(contaBase);
        assertEquals(saldo, contaBase.getSaldo());
        return contaBase;
    }

    protected XXConta obtemContaDoBanco(XXConta contaBase) {
        return repository.findContaByNumeroConta(contaBase.getNumeroConta())
                .orElseThrow(NullPointerException::new);
    }

}
