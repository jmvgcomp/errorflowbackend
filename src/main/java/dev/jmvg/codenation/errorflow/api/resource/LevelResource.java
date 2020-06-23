package dev.jmvg.codenation.errorflow.api.resource;

import dev.jmvg.codenation.errorflow.api.model.Level;
import dev.jmvg.codenation.errorflow.api.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/levels")
public class LevelResource {

    private final LevelRepository levelRepository;
    @Autowired
    public LevelResource(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @GetMapping
    public List<Level> findAll(){
        return levelRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Level> newLevel(@RequestBody Level level, HttpServletResponse response){
        Level levelSaved = levelRepository.save(level);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(levelSaved.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(levelSaved);
    }
    @GetMapping("/{id}")
    public Level getById(@PathVariable Integer id){
        return levelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
