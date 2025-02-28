package TADS_SEM4_BACK_END.Baixada_Santista.controller;

import TADS_SEM4_BACK_END.Baixada_Santista.dto.AuthRequestDTO;
import TADS_SEM4_BACK_END.Baixada_Santista.dto.UsuarioResponseDTO;
import TADS_SEM4_BACK_END.Baixada_Santista.model.Roles;
import TADS_SEM4_BACK_END.Baixada_Santista.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthRequestDTO request) {
        try {
//            var user = userRepository.findByEmail(request.email()).get();
            var user = userRepository.findByEmail(request.email()).orElse(null);

            if(user == null) {
                return ResponseEntity.status(404).body("Usuário não encontrado");
            }
            if(user.getRole() != Roles.ADMIN && user.getRole() != Roles.STOCKIST) {
                return ResponseEntity.status(401).body("Usuário não autorizado");
            }
            if(!user.getAtivo()) {
                return ResponseEntity.status(400).body("A conta deste usuário está desativada");
            }

//            log.info(String.valueOf(passwordEncoder.encode(request.senha()).equals(user.getSenha())));
            log.info(String.valueOf(passwordEncoder.matches(request.senha(), user.getSenha())));

            if (passwordEncoder.matches(request.senha(),user.getSenha()) && user.getEmail().equals(request.email())) {

                return ResponseEntity.ok().body(new UsuarioResponseDTO(user.getId(), user.getNickname(), user.getRole()));

            } else {
                return ResponseEntity.status(400).body("Email ou senha inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro ao logar: " + e.getMessage());
        }
    }

    @PostMapping("/trocarSenha")
    public ResponseEntity<String> trocarSenha(@RequestBody @Valid AuthRequestDTO request) {
        try{
            var user = userRepository.findByEmail(request.email()).orElse(null);

            if(user == null) {
                return ResponseEntity.status(404).body("Usuario não encontrado");
            }
            if(user.getSenha() == null) {
                return ResponseEntity.status(400).body("Senha vazia.");
            }
            log.info(String.valueOf(passwordEncoder.matches(request.senha(), user.getSenha())));

            if(passwordEncoder.matches(request.senha(),user.getSenha())) {
                return ResponseEntity.status(400).body("Senhas iguais");
            }
            else {
                user.setSenha(passwordEncoder.encode(request.senha()));
                userRepository.save(user);
                return ResponseEntity.status(200).body("Senha alterada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro no servidor: " + e.getMessage());
        }
    }

}

