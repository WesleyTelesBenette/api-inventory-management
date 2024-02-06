package com.wesleytelesbenette.apiinventorymanagement.dtos;

import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ProductCreateDto
{
    /**
     * O nome do produto.
     *
     * @NotEmpty O nome do produto não pode estar em branco.
     * @Size(min=1,max=50) O nome do produto só pode ter entre 1 e 50 caracteres.
     */
    @NotBlank(message = "O nome do produto não pode estar em branco.")
    @Size(min = 1, max = 50, message = "O nome do produto só pode ter entre 1 e 50 caracteres.")
    private String name;

    /**
     * A descrição do produto.
     */
    private String description = "";

    /**
     * O nome da categoria do produto.
     *
     * @NotEmpty O nome da categoria não pode estar em branco
     * @Size(min=1,max=30) O nome da categoria só pode ter entre 1 e 30 caracteres.
     */
    @NotBlank(message = "O nome da categoria não pode estar em branco.")
    @Size(min = 1, max = 30, message = "O nome da categoria só pode ter entre 1 e 30 caracteres.")
    private String categoryName;

    /**
     * O preço do produto.
     *
     * @NotNull O preço do produto não pode ser nulo.
     * @Size(min=0.0) O preço do produto não pode ser menor que "0.0".
     */
    @NotNull(message = "O preço do produto não pode ser nulo.")
    @DecimalMin(value = "0.0", message = "O preço do produto não pode ser menor que \"0.0\".")
    private Double price;

    /**
     * A quantidade do produto em estoque.
     *
     * @NotNull A quantidade do produto em estoque não pode ser nula.
     * @Size(min=0) A quantidade do produto em estoque não pode ser menor que 0.
     */
    @NotNull(message = "A quantidade do produto em estoque não pode ser nula.")
    @Min(value = 0, message = "A quantidade do produto em estoque não pode ser menor que 0.")
    private Integer amountStock;
}
