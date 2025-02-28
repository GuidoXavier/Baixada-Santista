package TADS_SEM4_BACK_END.Baixada_Santista.controller;

import TADS_SEM4_BACK_END.Baixada_Santista.dto.AuthRequestDTO;
import TADS_SEM4_BACK_END.Baixada_Santista.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping
    @JsonFormat
    public ResponseEntity authenticate(@RequestBody @Valid AuthRequestDTO request) {
        var token = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        var authenticated = authenticationManager.authenticate(token);
        return ResponseEntity.ok((User) authenticated.getPrincipal());
    }
}

