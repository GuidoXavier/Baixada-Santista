package TADS_SEM4_BACK_END.Baixada_Santista.Modelos;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private Roles role;

    private Boolean ativo;

    private String senha;

}