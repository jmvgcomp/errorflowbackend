package dev.jmvg.codenation.errorflow.api.repository;

import dev.jmvg.codenation.errorflow.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
