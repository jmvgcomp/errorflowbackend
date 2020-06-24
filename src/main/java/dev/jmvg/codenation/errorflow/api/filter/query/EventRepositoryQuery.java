package dev.jmvg.codenation.errorflow.api.filter.query;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepositoryQuery {
    Page<Event> filter(EventFilter eventFilter, Pageable pageable);
}