package dev.jmvg.codenation.errorflow.api.repository;

import dev.jmvg.codenation.errorflow.api.model.Event;
import dev.jmvg.codenation.errorflow.api.filter.query.EventRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryQuery {
}
