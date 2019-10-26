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

public class Elf {
    @Id
    private String elfId;
    private String name;
    private String race;
    private String elfClass;
}
