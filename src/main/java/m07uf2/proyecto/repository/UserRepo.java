package m07uf2.proyecto.repository;

import m07uf2.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <Usuario, Integer> {
    boolean existsByUsername(String username);
    Usuario findByUsername(String username);
}
