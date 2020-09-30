package br.com.sevencomm.cobranca.controllers;

import br.com.sevencomm.cobranca.models.Area;
import br.com.sevencomm.cobranca.services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/rest-api/v1/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(areaService.getById(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> getByName(@PathVariable("nome") String nome) {
        return ResponseEntity.ok(areaService.getByName(nome));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Area area) {
        return ResponseEntity.created(getURI(areaService.insert(area).getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Area area, @PathVariable("id") Long id) {
        Area aux = areaService.update(area, id);
        return aux != null ? ResponseEntity.ok(aux) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        areaService.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getURI(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
