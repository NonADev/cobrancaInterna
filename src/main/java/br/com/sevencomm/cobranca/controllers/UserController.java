package br.com.sevencomm.cobranca.controllers;

import br.com.sevencomm.cobranca.dtos.UserDTO;
import br.com.sevencomm.cobranca.models.User;
import br.com.sevencomm.cobranca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/info")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserDTO.create(user));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> getByID(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> post(@RequestBody User user){
        UserDTO dto = service.insert(user);
        URI location = getUri(0L);
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
