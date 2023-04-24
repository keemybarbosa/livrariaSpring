package tech.ada.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.banco.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>  {

}
