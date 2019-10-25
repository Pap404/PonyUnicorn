package com.petshop.enterpraise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data //создаем getter/setter для всех методов
@Document //связываем java-класс и базу данных

public class Unicorn {
 @Id //уникальный идентификатор, который позволит отличать один объект от другого
    private String id;
 private String name;
 private String magicSkills;
}
