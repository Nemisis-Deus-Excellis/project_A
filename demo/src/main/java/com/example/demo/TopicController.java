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
@RequestMapping("/Topic")
public class TopicController {
 
    @Autowired
    private TopicsService service;
 
    @GetMapping("/{id}")
    public Topic read(@PathVariable String id) {
        return service.find(id);
    }
    @PostMapping("/")
    public ResponseEntity<Topic> create(@RequestBody Topic topic) throws URISyntaxException {
        Topic createdTopic = service.create(topic);
        if (createdTopic == null) {
        return ResponseEntity.notFound().build();
    } else {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
    .path("/{id}")
        .buildAndExpand(createdTopic.getId())
        .toUri();
        return ResponseEntity.created(uri)
            .body(createdTopic);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Topic> update(@RequestBody Topic topic, @PathVariable Long id) {
        Topic updatedTopic = service.update(id, topic);
        if (updatedTopic == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedTopic);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTopic(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}