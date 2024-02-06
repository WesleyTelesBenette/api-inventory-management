package com.wesleytelesbenette.apiinventorymanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString //Debug
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TB_CATEGORIES")
public class Category
{
    /**
     * O identificador único da categoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome da categoria.
     *
     * @NotEmpty O nome da categoria não pode estar em branco
     * @Size(min=1,max=30) O nome da categoria só pode ter entre 1 e 30 caracteres.
     */
    @NotBlank(message = "O nome da categoria não pode estar em branco.")
    @Size(min = 1, max = 30, message = "O nome da categoria só pode ter entre 1 e 30 caracteres.")
    @Column(name = "name")
    private String name;

    public Category(String name)
    {
        this.name = name;
    }
}
