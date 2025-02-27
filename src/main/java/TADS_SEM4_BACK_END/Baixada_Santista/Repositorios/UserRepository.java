package TADS_SEM4_BACK_END.Baixada_Santista.Repositorios;

import TADS_SEM4_BACK_END.Baixada_Santista.Modelos.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
