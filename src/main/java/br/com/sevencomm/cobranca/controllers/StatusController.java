package br.com.sevencomm.cobranca.controllers;

import br.com.sevencomm.cobranca.models.Status;
import br.com.sevencomm.cobranca.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/rest-api/v1/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(statusService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(statusService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Status status) {
        return ResponseEntity.created(getURI(statusService.insert(status).getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Status status, @PathVariable("id") Long id){
        Status aux = statusService.update(status, id);
        return aux!=null ? ResponseEntity.ok(aux) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        statusService.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getURI(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
