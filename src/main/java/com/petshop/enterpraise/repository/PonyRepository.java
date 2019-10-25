package com.petshop.enterpraise.repository;

import com.petshop.enterpraise.model.Pony;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PonyRepository extends ReactiveMongoRepository<Pony, String> {

}
