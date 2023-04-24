package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.banco.model.TransacaoLivro;

@Repository
public interface TransacaoLivroRepository extends JpaRepository<TransacaoLivro, Long> {

}