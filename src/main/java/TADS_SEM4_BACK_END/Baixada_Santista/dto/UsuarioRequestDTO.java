package TADS_SEM4_BACK_END.Baixada_Santista.dto;

import lombok.Getter;

@Getter
public class UsuarioRequestDTO {
    private String nickname;
    private String cpf;
    private String email;
    private String senha;
    private Boolean ativo;
    private String role;
}
