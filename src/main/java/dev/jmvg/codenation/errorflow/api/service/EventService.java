package dev.jmvg.codenation.errorflow.api.service;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface EventService {
    Event updateEvent(Long id, Event event);
    List<Event> findAll(EventFilter eventFilter);
    ResponseEntity<Event> getByEventId(Long id);
    ResponseEntity<Event> createEvent(Event event, HttpServletResponse response);
    void removeEvent(Long id);
}
