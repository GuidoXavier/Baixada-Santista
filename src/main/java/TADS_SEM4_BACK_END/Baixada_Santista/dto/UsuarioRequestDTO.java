package TADS_SEM4_BACK_END.Baixada_Santista.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String nickname;
    private String cpf;
    private String email;
    private String senha;
    private Boolean ativo;
    private String role;
}
