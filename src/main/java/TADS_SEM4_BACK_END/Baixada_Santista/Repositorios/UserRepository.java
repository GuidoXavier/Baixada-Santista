package TADS_SEM4_BACK_END.Baixada_Santista.Repositorios;

import TADS_SEM4_BACK_END.Baixada_Santista.Modelos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByCpf(String cpf);
}
