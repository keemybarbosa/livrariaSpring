package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.banco.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}