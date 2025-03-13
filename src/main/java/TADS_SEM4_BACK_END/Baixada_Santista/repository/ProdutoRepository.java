package TADS_SEM4_BACK_END.Baixada_Santista.repository;

import TADS_SEM4_BACK_END.Baixada_Santista.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findBynomeProduto(String nomeProduto);
    Optional<Produto> findById(long id);
}
