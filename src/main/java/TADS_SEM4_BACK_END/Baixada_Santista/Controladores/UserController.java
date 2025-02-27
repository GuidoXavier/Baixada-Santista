package TADS_SEM4_BACK_END.Baixada_Santista.Controladores;

import TADS_SEM4_BACK_END.Baixada_Santista.Modelos.User;
import TADS_SEM4_BACK_END.Baixada_Santista.Repositorios.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(repository.findAll());
    }
    @PostMapping("/salvar")
    public ResponseEntity<User> salvarUsuario(@RequestBody User usuario) {
        return ResponseEntity.ok(repository.save(usuario));
    }

}

