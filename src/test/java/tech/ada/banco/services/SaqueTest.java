package tech.ada.banco.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.banco.exceptions.ResourceNotFoundException;
import tech.ada.banco.exceptions.SaldoInsuficienteException;
import tech.ada.banco.model.XXConta;
import tech.ada.banco.model.XXModalidadeConta;
import tech.ada.banco.repository.XXContaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaqueTest {

    private final XXContaRepository repository = Mockito.mock(XXContaRepository.class);
    private final XXSaque saque = new XXSaque(repository);

    @Test
    void testSaqueParcial() {
        XXConta conta = new XXConta(XXModalidadeConta.CC, null);
        conta.deposito(BigDecimal.TEN);
        when(repository.findContaByNumeroConta(10)).thenReturn(Optional.of(conta));
        assertEquals(BigDecimal.valueOf(10), conta.getSaldo(), "O saldo inicial da conta deve ser alterado para 10");

        BigDecimal resp = saque.executar(10, BigDecimal.ONE);

        verify(repository, times(1)).save(conta);
        assertEquals(BigDecimal.valueOf(9).setScale(2), resp, "O valor de retorno da função tem que ser 9. Saldo " +
                "anterior " +
                "vale 10 e o valor de saque é 1");
        assertEquals(BigDecimal.valueOf(9).setScale(2), conta.getSaldo());
    }

    @Test
    void testSaqueContaNaoEncontrada() {
        XXConta conta = new XXConta(XXModalidadeConta.CC, null);
        conta.deposito(BigDecimal.TEN);
        when(repository.findContaByNumeroConta(10)).thenReturn(Optional.of(conta));
        assertEquals(BigDecimal.valueOf(10), conta.getSaldo(), "O saldo inicial da conta deve ser alterado para 10");

        try {
            saque.executar(1, BigDecimal.ONE);
            fail("A conta deveria não ter sido encontrada.");
        } catch (ResourceNotFoundException e) {

        }

        verify(repository, times(0)).save(any());
        assertEquals(BigDecimal.valueOf(10), conta.getSaldo(), "O saldo da conta não pode ter sido alterado.");
    }

    @Test
    void testSaqueProblemaDeBancoDeDados() {
        XXConta conta = new XXConta(XXModalidadeConta.CC, null);
        conta.deposito(BigDecimal.TEN);
        when(repository.findContaByNumeroConta(10)).thenThrow(RuntimeException.class);
        assertEquals(BigDecimal.valueOf(10), conta.getSaldo(), "O saldo inicial da conta deve ser alterado para 10");

        try {
            saque.executar(1, BigDecimal.ONE);
            fail("A conta deveria não ter sido encontrada. Por problema de conexão de banco de dados");
        } catch (RuntimeException e) {

        }

        verify(repository, times(0)).save(any());
        assertEquals(BigDecimal.valueOf(10), conta.getSaldo(), "O saldo da conta não pode ter sido alterado.");
    }

    @Test
    void testSaqueMaiorSaldo() {
        XXConta conta = new XXConta(XXModalidadeConta.CC, null);
        conta.deposito(BigDecimal.valueOf(5));
        when(repository.findContaByNumeroConta(10)).thenReturn(Optional.of(conta));
        assertEquals(BigDecimal.valueOf(5), conta.getSaldo(), "O saldo inicial da conta deve ser alterado para 5");

        assertThrows(SaldoInsuficienteException.class, () -> saque.executar(10, BigDecimal.valueOf(6)));
        verify(repository, times(0)).save(any());
        assertEquals(BigDecimal.valueOf(5), conta.getSaldo(), "O saldo da conta não se alterou");

    }

    @Test
    void testSaqueArredondamentoParaBaixo() {
        XXConta conta = criarConta(10, 100);

        BigDecimal resp = saque.executar(100, BigDecimal.valueOf(2.134956789));

        verify(repository, times(1)).save(conta);
        assertEquals(BigDecimal.valueOf(7.87), resp, "O valor subtraído foi arredondado para 2.13");
        assertEquals(BigDecimal.valueOf(7.87), conta.getSaldo());
    }

    @Test
    void testSaqueArredondamentoParaCima() {
        XXConta conta = criarConta(10, 100);

        BigDecimal resp = saque.executar(100, BigDecimal.valueOf(2.135456789));

        verify(repository, times(1)).save(conta);
        assertEquals(BigDecimal.valueOf(7.86), resp, "O valor subtraído foi arredondado para 2.14");
        assertEquals(BigDecimal.valueOf(7.86), conta.getSaldo());
    }

    private XXConta criarConta(double valor, int numeroConta) {
        XXConta conta = new XXConta(XXModalidadeConta.CC, null);
        conta.deposito(BigDecimal.valueOf(valor));
        when(repository.findContaByNumeroConta(numeroConta)).thenReturn(Optional.of(conta));
        assertEquals(BigDecimal.valueOf(valor), conta.getSaldo(),
                "O saldo inicial da conta deve ser alterado para " + valor);
        return conta;
    }

}