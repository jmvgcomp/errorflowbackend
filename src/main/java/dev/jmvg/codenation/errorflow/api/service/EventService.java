package dev.jmvg.codenation.errorflow.api.service;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface EventService {
    Event updateEvent(Long id, Event event);
    Page<Event> findAll(EventFilter eventFilter, Pageable pageable);
    ResponseEntity<Event> getByEventId(Long id);
    ResponseEntity<Event> createEvent(Event event, HttpServletResponse response);
    void removeEvent(Long id);
}
