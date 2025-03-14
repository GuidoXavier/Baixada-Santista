package TADS_SEM4_BACK_END.Baixada_Santista.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Produto produto;
}
