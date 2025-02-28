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

    @PostMapping("/salvar")
    public ResponseEntity<User> salvarUsuario(@RequestBody User usuario) {
        return ResponseEntity.ok(repository.save(usuario));
    }


    @GetMapping("/listarTodos")
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(repository.findAll());
    }

    //Recebo o usuário pelo ID, procuro o id, se eu achar eu devolvo os dados dele
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Recebo o usuário que vou editar pelo ID e recebo os novos dados pelo RequestBody
    //Procuro o ID e se encontrar eu atualizo com os novos dados
    @PutMapping("/editar/{id}")
    public ResponseEntity<User> editarUsuario(@PathVariable Long id, @RequestBody User usuarioAtualizado) {
        return repository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNickname(usuarioAtualizado.getNickname());
                    usuarioExistente.setEmail(usuarioAtualizado.getEmail());
                    usuarioExistente.setRole(usuarioAtualizado.getRole());
                    usuarioExistente.setAtivo(usuarioAtualizado.getAtivo());
                    usuarioExistente.setSenha(usuarioAtualizado.getSenha());
                    User usuarioSalvo = repository.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

