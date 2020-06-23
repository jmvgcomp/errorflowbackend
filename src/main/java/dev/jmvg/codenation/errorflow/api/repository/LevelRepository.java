package dev.jmvg.codenation.errorflow.api.repository;

import dev.jmvg.codenation.errorflow.api.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Integer> {
}
