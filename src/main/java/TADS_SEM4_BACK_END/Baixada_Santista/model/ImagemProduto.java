package TADS_SEM4_BACK_END.Baixada_Santista.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imagem-produtos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String URL;

    @Column
    private boolean imagemPrincipal;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
}
