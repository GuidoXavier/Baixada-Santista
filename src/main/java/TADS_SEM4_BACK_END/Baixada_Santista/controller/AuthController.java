package TADS_SEM4_BACK_END.Baixada_Santista.controller;

import TADS_SEM4_BACK_END.Baixada_Santista.dto.AuthRequestDTO;
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

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody @Valid AuthRequestDTO request) {
        try {
            var user = userRepository.findByEmail(request.email()).get();
            log.info(String.valueOf(passwordEncoder.encode(request.senha()).equals(user.getSenha())));

            if (passwordEncoder.matches(request.senha(),user.getSenha()) && user.getEmail().equals(request.email())) {

                return ResponseEntity.ok("Usuário autenticado");
            } else {
                return ResponseEntity.status(401).body("Email ou senha inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro ao logar: " + e.getMessage());
        }
    }
}

