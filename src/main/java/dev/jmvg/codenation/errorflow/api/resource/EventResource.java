package dev.jmvg.codenation.errorflow.api.resource;

import dev.jmvg.codenation.errorflow.api.filter.EventFilter;
import dev.jmvg.codenation.errorflow.api.model.Event;
import dev.jmvg.codenation.errorflow.api.service.implementation.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/events")
public class EventResource {

    private final EventServiceImpl eventServiceImpl;

    @Autowired
    public EventResource(EventServiceImpl eventServiceImpl) {
        this.eventServiceImpl = eventServiceImpl;
    }

    @GetMapping
    public List<Event> findAllEvents(EventFilter eventFilter){
        return eventServiceImpl.findAll(eventFilter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getByEventId(@PathVariable("id") Long id){
        return eventServiceImpl.getByEventId(id);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, HttpServletResponse response){
        return eventServiceImpl.createEvent(event, response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEvent(@PathVariable("id") Long id){
        eventServiceImpl.removeEvent(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @Valid @RequestBody Event event){
        Event savedEvent = eventServiceImpl.updateEvent(id, event);
        return ResponseEntity.ok(savedEvent);
    }

}
