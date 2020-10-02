package br.com.sevencomm.cobranca.application.controllers;

import br.com.sevencomm.cobranca.domain.interfaces.IStatusService;
import br.com.sevencomm.cobranca.domain.models.Status;
import br.com.sevencomm.cobranca.domain.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/rest-api/v1/status")
public class StatusController {
    @Autowired
    private IStatusService statusService;

    @GetMapping
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity.ok(statusService.listAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(statusService.getStatusById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Status status) {
        try {
            return ResponseEntity.created(getURI(statusService.insertStatus(status).getId())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Status status, @PathVariable("id") Integer id) {
        try {
            Status aux = statusService.updateStatus(status, id);
            return aux != null ? ResponseEntity.ok(aux) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            statusService.deleteStatus(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    private URI getURI(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    private static class Error {
        public String error;

        public Error(String error) {
            this.error = error;
        }
    }
}
