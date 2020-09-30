package br.com.sevencomm.cobranca.controllers;

import br.com.sevencomm.cobranca.models.CobrancaInterna;
import br.com.sevencomm.cobranca.services.CobrancaInternaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/rest-api/v1/cobranca-interna")
public class CobrancaInternaController {
    @Autowired
    private CobrancaInternaService cobrancaInternaService;

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(cobrancaInternaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(cobrancaInternaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CobrancaInterna cobrancaInterna){
        return ResponseEntity.created(getURI(cobrancaInternaService.insert(cobrancaInterna).getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody CobrancaInterna cobrancaInterna, @PathVariable("id") Long id){
        CobrancaInterna aux = cobrancaInternaService.update(cobrancaInterna, id);
        return aux != null ? ResponseEntity.ok(aux) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        cobrancaInternaService.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getURI(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
