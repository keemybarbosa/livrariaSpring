package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.banco.model.XXPessoa;

@Repository
public interface XXPessoaRepository extends JpaRepository<XXPessoa, Integer> {
}
