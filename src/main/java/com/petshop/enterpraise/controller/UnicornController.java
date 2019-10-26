package com.petshop.enterpraise.controller;

import com.petshop.enterpraise.config.UnicornNotFoundEx;
import com.petshop.enterpraise.model.Unicorn;
import com.petshop.enterpraise.repository.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController //обрабатывает класс, содержит методы, которые будут обрабатывать HTTP-запросы
@RequestMapping("/api")

public class UnicornController {
    @Autowired //Spring реализует интерфейс и даст нам на него ссылку
    private UnicornRepository repository;
    @GetMapping("/unicorn")
        public Flux<Unicorn> getAll(){
        return repository.findAll();
    }

    @GetMapping("/unicorn/{id}")
        public Mono<Unicorn> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping("/unicorn")
//    {
//        "id": "1",
//            "name": "Rainbow",
//            "magicSkills": "Flash"
//    }
//    Данные приходят в формате JSON, а их с помощью аннотации @RequestBody
    public Mono<Unicorn> create(@RequestBody Unicorn unicorn) { //@RequestBody - принимаем тело запроса и интерпретируем его как экземпляр класса Unicorn
        return repository.save(unicorn);
    }

    @PutMapping("/unicorn/{id}")
    public Mono<Unicorn> updateUnicorn(@RequestBody Unicorn newUnicorn, @PathVariable String id) {
        return repository.findById(id).flatMap(oldUnicorn -> {
            oldUnicorn.setName(newUnicorn.getName());
            oldUnicorn.setMagicSkills(newUnicorn.getMagicSkills());
            return repository.save(oldUnicorn);
        });
    }

    @DeleteMapping("/unicorn/{id}")
    public Mono<Void> deleteUnicorn(@PathVariable String id) {
        return repository.deleteById(id);
    }
}
