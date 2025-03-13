package TADS_SEM4_BACK_END.Baixada_Santista.repository;

import TADS_SEM4_BACK_END.Baixada_Santista.model.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagensRepository extends JpaRepository<ImagemProduto, Long> {
    List<ImagemProduto> findByProdutoId(Long produtoId);
}
