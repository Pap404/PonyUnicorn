package com.petshop.enterpraise.controller;
import com.petshop.enterpraise.model.Elf;
import com.petshop.enterpraise.repository.ElfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class ElfController {
    @Autowired
    private ElfRepository elfRepository;

    @GetMapping("/elf")
    public Flux<Elf> getAll() {
        return elfRepository.findAll();
    }

    @GetMapping("/elf/{elfId}")
    public Mono<Elf> getElfById(@PathVariable String elfId){
        return elfRepository.findById(elfId);
    }

    @PostMapping("/elf")
    public Mono<Elf> createElf(@RequestBody Elf elf){
        return elfRepository.save(elf);
    }

    @PutMapping("/elf/{elfId}")
    public Mono<Elf> updateElf(@RequestBody Elf newElf, @PathVariable String elfId) {
        return elfRepository.findById(elfId).flatMap(oldElf -> {
            oldElf.setName(newElf.getName());
            oldElf.setRace(newElf.getRace());
            oldElf.setElfClass(newElf.getElfClass());
            return elfRepository.save(oldElf);
        });
    }

    @DeleteMapping("/elf/{elfId}")
    public Mono<Void> deleteElf(@PathVariable String elfId) {
        return elfRepository.deleteById(elfId);
    }
}