package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.banco.model.XXConta;

import java.util.Optional;

@Repository
public interface XXContaRepository extends JpaRepository<XXConta, Integer> {

    Optional<XXConta> findContaByNumeroConta(int numeroConta);
}
