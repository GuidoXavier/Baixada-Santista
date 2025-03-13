package TADS_SEM4_BACK_END.Baixada_Santista.dto;

import TADS_SEM4_BACK_END.Baixada_Santista.model.ImagemProduto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ProdutoResponseDTO {

    private long id;
    private String nomeProduto;
    private BigDecimal avaliacao;
    private String descricao;
    private BigDecimal preco;
    private int quantEstoque;
    private boolean ativo;
    private List<ImagemProduto> imagens;

}
