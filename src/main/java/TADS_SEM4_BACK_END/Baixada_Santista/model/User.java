package TADS_SEM4_BACK_END.Baixada_Santista.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "USERS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    @CPF(message = "O cpf informado é inválido")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private Boolean ativo;

    private String senha;

}