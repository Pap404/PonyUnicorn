package com.petshop.enterpraise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document

public class Pony {
    @Id
    private String ponyId;
    private String name;
    private String lear;
    private String old;
}
