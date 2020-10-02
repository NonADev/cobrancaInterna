package br.com.sevencomm.cobranca.application.controllers;

import br.com.sevencomm.cobranca.application.dtos.UserDTO;
import br.com.sevencomm.cobranca.domain.interfaces.IUserService;
import br.com.sevencomm.cobranca.domain.models.User;
import br.com.sevencomm.cobranca.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/rest-api/v1/users")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity.ok(service.listUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping("/get-current-user")
    public ResponseEntity<?> getCurrentUserInfo() {
        try {
            return ResponseEntity.ok(service.getCurrentUser());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(UserDTO.create(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    //@Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> getByID(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(service.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping("/area/{area_id}")
    public ResponseEntity<?> getByAreaId(@PathVariable("area_id") Integer id) {
        try {
            return ResponseEntity.ok(service.getUsersByArea(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    //@Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @PostMapping("/sign-up")
    //@Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> post(@RequestBody User user) {
        try {
            UserDTO dto = service.insertUser(user);
            URI location = getUri(dto.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    private URI getUri(Integer id) {
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
