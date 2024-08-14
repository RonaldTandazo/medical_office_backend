package controller;

import entities.Colaborador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ColaboradorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping
    public List<Colaborador> getAllColaboradores() {
        return colaboradorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> getColaboradorById(@PathVariable Long id) {
        Optional<Colaborador> colaborador = colaboradorService.findById(id);
        return colaborador.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Colaborador createColaborador(@RequestBody Colaborador colaborador) {
        return colaboradorService.save(colaborador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> updateColaborador(@PathVariable Long id, @RequestBody Colaborador colaboradorDetails) {
        Optional<Colaborador> colaborador = colaboradorService.findById(id);
        if (colaborador.isPresent()) {
            Colaborador updatedColaborador = colaborador.get();
            updatedColaborador.setNombre(colaboradorDetails.getNombre());
            updatedColaborador.setPuesto(colaboradorDetails.getPuesto());
            updatedColaborador.setEmail(colaboradorDetails.getEmail());
            updatedColaborador.setTelefono(colaboradorDetails.getTelefono());
            colaboradorService.save(updatedColaborador);
            return ResponseEntity.ok(updatedColaborador);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        colaboradorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
