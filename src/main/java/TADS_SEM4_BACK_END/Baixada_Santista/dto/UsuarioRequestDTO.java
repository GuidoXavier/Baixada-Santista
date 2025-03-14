package TADS_SEM4_BACK_END.Baixada_Santista.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String name;
    @CPF(message = "CPF invalido")
    private String cpf;
    private String email;
    private String senha;
    private Boolean ativo;
    private String role;
}
