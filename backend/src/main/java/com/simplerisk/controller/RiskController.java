package com.simplerisk.controller;

import com.simplerisk.entity.Risk;
import com.simplerisk.service.RiskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Presentation Layer: REST-API für das Vue.js Frontend.
 * Basis-URL: /api (context-path), Ressource: /risks → /api/risks
 */
@RestController
@RequestMapping("/risks")
public class RiskController {

    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping
    public List<Risk> getAll() {
        return riskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Risk> getById(@PathVariable Long id) {
        return riskService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "status")
    public List<Risk> getByStatus(@RequestParam Risk.RiskStatus status) {
        return riskService.findByStatus(status);
    }

    @PostMapping
    public ResponseEntity<Risk> create(@Valid @RequestBody Risk risk) {
        Risk created = riskService.create(risk);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Risk> update(@PathVariable Long id, @Valid @RequestBody Risk risk) {
        try {
            Risk updated = riskService.update(id, risk);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        riskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
