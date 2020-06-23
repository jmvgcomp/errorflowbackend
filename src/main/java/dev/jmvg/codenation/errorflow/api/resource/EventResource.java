package dev.jmvg.codenation.errorflow.api.resource;

import dev.jmvg.codenation.errorflow.api.event.ResourceCreatedEvent;
import dev.jmvg.codenation.errorflow.api.model.Event;
import dev.jmvg.codenation.errorflow.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/events")
public class EventResource {
    private final EventRepository eventRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public EventResource(EventRepository eventRepository, ApplicationEventPublisher publisher) {
        this.eventRepository = eventRepository;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Event> findAllEvents(){
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getByEventId(@PathVariable("id") Long id){
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, HttpServletResponse response){
        Event savedEvent = eventRepository.save(event);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, savedEvent.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }
}
