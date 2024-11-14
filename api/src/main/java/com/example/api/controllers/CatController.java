package com.example.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private List<Cat> cats = new ArrayList<>();

    public CatController() {
        cats.add(new Cat("Whiskers", "Siamês"));
    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats() {
        if (cats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return new ResponseEntity<>(cats, HttpStatus.OK); 
    }

    @GetMapping("/{name}")
    public ResponseEntity<Cat> getCat(@PathVariable String name) {
        Optional<Cat> cat = cats.stream().filter(c -> c.getName().equals(name)).findFirst();
        if (cat.isPresent()) {
            return new ResponseEntity<>(cat.get(), HttpStatus.OK); 
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
    }

    @PostMapping
    public ResponseEntity<String> createCat(@RequestBody Cat cat) {
        cats.add(cat); 
        return new ResponseEntity<>("Gato criado com sucesso!", HttpStatus.CREATED); 
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateCat(@PathVariable String name, @RequestBody Cat cat) {
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getName().equals(name)) {
                cats.set(i, cat); 
                return new ResponseEntity<>("Gato atualizado com sucesso!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Gato não encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCat(@PathVariable String name) {
        boolean removed = cats.removeIf(cat -> cat.getName().equals(name));
        if (removed) {
            return new ResponseEntity<>("Gato deletado com sucesso!", HttpStatus.NO_CONTENT); 
        } else {
            return new ResponseEntity<>("Gato não encontrado!", HttpStatus.NOT_FOUND); 
        }
    }
}
