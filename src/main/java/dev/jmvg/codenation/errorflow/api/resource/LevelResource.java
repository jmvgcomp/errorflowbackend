package dev.jmvg.codenation.errorflow.api.resource;

import dev.jmvg.codenation.errorflow.api.event.ResourceCreatedEvent;
import dev.jmvg.codenation.errorflow.api.model.Level;
import dev.jmvg.codenation.errorflow.api.repository.LevelRepository;
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
@RequestMapping("/v1/levels")
public class LevelResource {

    private final LevelRepository levelRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public LevelResource(LevelRepository levelRepository, ApplicationEventPublisher publisher) {
        this.levelRepository = levelRepository;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Level> findAll(){
        return levelRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Level> newLevel(@Valid @RequestBody Level level, HttpServletResponse response){
        Level levelSaved = levelRepository.save(level);

        publisher.publishEvent(new ResourceCreatedEvent(this, response, levelSaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(levelSaved);
    }
    @GetMapping("/{id}")
    public Level getById(@PathVariable Integer id){
        return levelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
