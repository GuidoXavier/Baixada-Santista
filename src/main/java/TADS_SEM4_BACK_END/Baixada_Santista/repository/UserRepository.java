package TADS_SEM4_BACK_END.Baixada_Santista.repository;

import TADS_SEM4_BACK_END.Baixada_Santista.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(String email);
     Optional<User> findByCpf(String cpf);
}
