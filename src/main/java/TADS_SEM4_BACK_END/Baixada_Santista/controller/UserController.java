package TADS_SEM4_BACK_END.Baixada_Santista.controller;

import TADS_SEM4_BACK_END.Baixada_Santista.model.Roles;
import TADS_SEM4_BACK_END.Baixada_Santista.model.User;
import TADS_SEM4_BACK_END.Baixada_Santista.repository.UserRepository;
import TADS_SEM4_BACK_END.Baixada_Santista.dto.UsuarioRequestDTO;
import TADS_SEM4_BACK_END.Baixada_Santista.dto.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody UsuarioRequestDTO requestDTO) {
        System.out.println("Recebido: " + requestDTO.getNickname() + ", " + requestDTO.getSenha());
        try {
            //aqui eu verifico se o email existe ou não no banco
            if (repository.findByEmail(requestDTO.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }
            //msm coisa só que pra CPF
            if (repository.findByCpf(requestDTO.getCpf()).isPresent()) {
                return ResponseEntity.badRequest().body("CPF já cadastrado");
            }

            //crio o usuário
            User novoUsuario = new User();
            novoUsuario.setNickname(requestDTO.getNickname());
            novoUsuario.setEmail(requestDTO.getEmail());
            novoUsuario.setSenha(passwordEncoder.encode(requestDTO.getSenha()));
            novoUsuario.setCpf(requestDTO.getCpf());

            //converto string role para enum Roles
            try {
                Roles role = Roles.valueOf(requestDTO.getRole().toUpperCase());
                novoUsuario.setRole(role);
            } catch (IllegalArgumentException e) {
                //se o role fornecido não for válido, definir como USER (vai que né)
                novoUsuario.setRole(Roles.USER);
            }

            //e aqui defino o resto para padrão que é true
            novoUsuario.setAtivo(true);
            //salva usuário
            User usuarioSalvo = repository.save(novoUsuario);

            //retorno ResponseDTO em vez do objeto entidade (não entendi muito bem isso, peguei da net)
            return ResponseEntity.ok(new UsuarioResponseDTO(usuarioSalvo));
        } catch (Exception e) {
            //caso der ruim
            return ResponseEntity.badRequest().body("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(repository.findAll());
    }

    //recebo o usuário pelo ID, procuro o id, se eu achar eu devolvo os dados dele
    @GetMapping("/buscar/{id}")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Object> editarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO requestDTO) {
        Optional<User> usuarioOptional = repository.findById(id);
        //TENTEI COM MAP/ORELSE E FRACASSEI (CASO ALGUEM CONSIGA EU ACHO QUE FICARIA MELHOR)
        if (usuarioOptional.isPresent()) {
            try {
                User usuarioExistente = usuarioOptional.get();

                //verifico se o email já está cadastrado por outro usuário (SEM SER ESSE)
                if (repository.findByEmail(requestDTO.getEmail()).filter(user -> user.getId() != id).isPresent()) {
                    return ResponseEntity.badRequest().body("Email já cadastrado");
                }

                //verifico se o CPF já está cadastrado por outro usuário (SEM SER ESSE)
                if (repository.findByCpf(requestDTO.getCpf()).filter(user -> user.getId() != id).isPresent()) {
                    return ResponseEntity.badRequest().body("CPF já cadastrado");
                }

                usuarioExistente.setNickname(requestDTO.getNickname());
                usuarioExistente.setEmail(requestDTO.getEmail());

                //mudo a senha apenas se for fornecida
                if (requestDTO.getSenha() != null && !requestDTO.getSenha().isEmpty()) {
                    usuarioExistente.setSenha(requestDTO.getSenha());
                }

                //novamente converto string role para enum Roles
                try {
                    if (requestDTO.getRole() != null) {
                        Roles role = Roles.valueOf(requestDTO.getRole().toUpperCase());
                        usuarioExistente.setRole(role);
                    }
                } catch (IllegalArgumentException e) {
                    //e ignoro se o role não for válido
                }
                usuarioExistente.setAtivo(requestDTO.getAtivo());

                User usuarioSalvo = repository.save(usuarioExistente);
                return ResponseEntity.ok(new UsuarioResponseDTO(usuarioSalvo));
            } catch (Exception e) {
                //se der ruim
                return ResponseEntity.badRequest().body("Erro ao editar usuário: " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

