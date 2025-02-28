package TADS_SEM4_BACK_END.Baixada_Santista.dto;

import lombok.Getter;

@Getter
public class UsuarioRequestDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String tipoUsuario;
}
