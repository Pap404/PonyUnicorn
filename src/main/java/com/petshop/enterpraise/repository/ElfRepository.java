package com.petshop.enterpraise.repository;

import com.petshop.enterpraise.model.Elf;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ElfRepository extends ReactiveMongoRepository<Elf, String> {

}
