package TADS_SEM4_BACK_END.Baixada_Santista.dto;

import TADS_SEM4_BACK_END.Baixada_Santista.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nickname;
    private String email;
    private String role;
    private String cpf;

    public UsuarioResponseDTO(User usuario) {
        this.id = usuario.getId();
        this.nickname = usuario.getNickname();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.role = usuario.getRole().toString();
    }
}
