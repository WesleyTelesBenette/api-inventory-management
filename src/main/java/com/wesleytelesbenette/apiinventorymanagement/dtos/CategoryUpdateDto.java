package com.wesleytelesbenette.apiinventorymanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CategoryUpdateDto
{
    /**
     * O nome da categoria antiga.
     *
     * @NotEmpty O nome da categoria não pode estar em branco
     * @Size(min=1,max=30) O nome da categoria só pode ter entre 1 e 30 caracteres.
     */
    @NotBlank(message = "O nome da categoria não pode estar em branco.")
    @Size(min = 1, max = 30, message = "O nome da categoria só pode ter entre 1 e 30 caracteres.")
    String oldCategory;

    /**
     * O nome da categoria nova.
     *
     * @NotEmpty O nome da categoria não pode estar em branco
     * @Size(min=1,max=30) O nome da categoria só pode ter entre 1 e 30 caracteres.
     */
    @NotBlank(message = "O nome da categoria não pode estar em branco.")
    @Size(min = 1, max = 30, message = "O nome da categoria só pode ter entre 1 e 30 caracteres.")
    String newCategory;
}
