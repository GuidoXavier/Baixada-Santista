package TADS_SEM4_BACK_END.Baixada_Santista.Controladores;

import TADS_SEM4_BACK_END.Baixada_Santista.Modelos.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.regex.Pattern;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = "\\d{11}", message = "CPF inválido")
    private String cpf;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String senha;

    @Transient
    @NotBlank
    private String confirmarSenha;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Column(nullable = false)
    private boolean ativo = true;

    public void setSenha(String senha, String confirmarSenha) {
        if (!senha.equals(confirmarSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem");
        }
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }
}

interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}

@Service
class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if (!validarCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }
        usuario.setSenha(usuario.getSenha(), usuario.getConfirmarSenha());
        return usuarioRepository.save(usuario);
    }

    private boolean validarCpf(String cpf) {
        if (!Pattern.matches("\\d{11}", cpf)) {
            return false;
        }
        int[] pesos1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int primeiroDigito = calcularDigitoVerificador(cpf.substring(0, 9), pesos1);
        int segundoDigito = calcularDigitoVerificador(cpf.substring(0, 9) + primeiroDigito, pesos2);

        return cpf.equals(cpf.substring(0, 9) + primeiroDigito + segundoDigito);
    }

    private int calcularDigitoVerificador(String str, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += (str.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}

@RestController
@RequestMapping("/usuarios")
class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
