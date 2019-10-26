package com.petshop.enterpraise.controller;

import com.petshop.enterpraise.model.Pony;
import com.petshop.enterpraise.repository.PonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class PonyController {
    @Autowired
    private PonyRepository ponyRepository;

    @GetMapping("/pony")
    public Flux<Pony> getAll() {
        return ponyRepository.findAll();
    }

    @GetMapping("/pony/{ponyId}")
    public Mono<Pony> getPonyById(@PathVariable String ponyId){
        return ponyRepository.findById(ponyId);
    }

    @PostMapping("/pony")
    public Mono<Pony> createPony(@RequestBody Pony pony){
        return ponyRepository.save(pony);
    }

    @PutMapping("/pony/{ponyId}")
    public Mono<Pony> updatePony(@RequestBody Pony newPony, @PathVariable String ponyId) {
        return ponyRepository.findById(ponyId).flatMap(oldPony -> {
         oldPony.setName(newPony.getName());
         oldPony.setLear(newPony.getLear());
         oldPony.setOld(newPony.getOld());
        return ponyRepository.save(oldPony);
        });
    }

    @DeleteMapping("/pony/{ponyId}")
    public Mono<Void> deletePony(@PathVariable String ponyId) {
        return ponyRepository.deleteById(ponyId);
    }
}
