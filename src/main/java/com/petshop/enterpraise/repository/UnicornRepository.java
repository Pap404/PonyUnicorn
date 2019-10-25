package com.petshop.enterpraise.repository;

import com.petshop.enterpraise.model.Unicorn;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UnicornRepository extends ReactiveMongoRepository<Unicorn, String> {

}
