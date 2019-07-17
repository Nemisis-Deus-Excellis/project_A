package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/DemoModel")
public class DemoModelController {
 
    @Autowired
    private DemoModelsService service;
 
    @GetMapping("/{id}")
    public DemoModel read(@PathVariable String id) {
        return service.find(id);
    }
    @PostMapping("/")
    public ResponseEntity<DemoModel> create(@RequestBody DemoModel DemoModel) throws URISyntaxException {
        DemoModel createdDemoModel = service.create(DemoModel);
        if (createdDemoModel == null) {
        return ResponseEntity.notFound().build();
    } else {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
    .path("/{id}")
        .buildAndExpand(createdDemoModel.getId())
        .toUri();
        return ResponseEntity.created(uri)
            .body(createdDemoModel);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DemoModel> update(@RequestBody DemoModel DemoModel, @PathVariable Long id) {
        DemoModel updatedDemoModel = service.update(id, DemoModel);
        if (updatedDemoModel == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedDemoModel);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDemoModel(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}