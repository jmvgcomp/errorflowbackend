package dev.jmvg.codenation.errorflow.api.query;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;

import java.util.List;

public interface EventRepositoryQuery {
    List<Event> filter(EventFilter eventFilter);
}