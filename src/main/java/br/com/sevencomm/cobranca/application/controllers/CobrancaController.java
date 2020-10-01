package br.com.sevencomm.cobranca.application.controllers;

import br.com.sevencomm.cobranca.domain.interfaces.ICobrancaService;
import br.com.sevencomm.cobranca.domain.models.Cobranca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/rest-api/v1/cobranca-interna")
public class CobrancaController {

    @Autowired
    private ICobrancaService cobrancaInternaService;

    @GetMapping
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity.ok(cobrancaInternaService.listCobrancas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCobrancaById(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(cobrancaInternaService.getCobranca(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-cobrancadas-recebidas")
    public ResponseEntity<?> getCobrancasRecebidas(@RequestParam("cobrancaId") Integer cobrancaId){
        try {
            return ResponseEntity.ok(cobrancaInternaService.listCobrancas(cobrancaId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/beneficiario/{id}")
    public ResponseEntity<?> getCobrancasEnviadas(@PathVariable("id") Integer id){
        try{
            //return ResponseEntity.ok(cobrancaInternaService.getAllByBeneficiarioAreaId(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getByStatusId(@PathVariable("id") Integer id){
        try{
            //return ResponseEntity.ok(cobrancaInternaService.getAllByStatusId(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Cobranca cobrancaInterna) {
        try {
            return ResponseEntity.created(getURI(cobrancaInternaService.insertCobranca(cobrancaInterna).getId())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody Cobranca cobrancaInterna, @PathVariable("id") Integer id) {
        try {
            //Cobranca aux = cobrancaInternaService.update(cobrancaInterna, id);
            //return ResponseEntity.ok(aux);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            cobrancaInternaService.deleteCobranca(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private URI getURI(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
