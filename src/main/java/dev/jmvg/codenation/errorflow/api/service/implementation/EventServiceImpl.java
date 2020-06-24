package dev.jmvg.codenation.errorflow.api.service.implementation;

import dev.jmvg.codenation.errorflow.api.event.ResourceCreatedEvent;
import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import dev.jmvg.codenation.errorflow.api.repository.EventRepository;
import dev.jmvg.codenation.errorflow.api.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ApplicationEventPublisher publisher) {
        this.eventRepository = eventRepository;
        this.publisher = publisher;
    }

    @Override
    public Page<Event> findAll(EventFilter eventFilter, Pageable pageable) {
        return eventRepository.filter(eventFilter, pageable);
    }


    @Override
    public ResponseEntity<Event> getByEventId(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return ResponseEntity.ok(event);
    }

    @Override
    public ResponseEntity<Event> createEvent(Event event, HttpServletResponse response) {
        Event savedEvent = eventRepository.save(event);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, savedEvent.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @Override
    public Event updateEvent(Long id, Event event){
        Event savedEvent = eventRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(event, savedEvent, "id", "createdAt");
        return eventRepository.save(savedEvent);
    }

    @Override
    public void removeEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        eventRepository.delete(event);
    }


}
