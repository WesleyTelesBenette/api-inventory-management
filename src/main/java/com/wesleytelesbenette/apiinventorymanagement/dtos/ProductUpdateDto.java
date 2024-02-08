package com.wesleytelesbenette.apiinventorymanagement.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductUpdateDto
{
    /**
     * O identificador único do produto.
     *
     * @NotNull O id do produto não pode ser nulo.
     */
    @NotNull
    private Long id;

    /**
     * O nome do produto.
     *
     * @Size(min=1,max=50) O nome do produto só pode ter entre 1 e 50 caracteres.
     */
    @Size(min = 1, max = 50, message = "O nome do produto só pode ter entre 1 e 50 caracteres.")
    private String name = null;

    /**
     * A descrição do produto.
     */
    private String description = null;

    /**
     * O nome da categoria do produto.
     *
     * @Size(min=1,max=30) O nome da categoria só pode ter entre 1 e 30 caracteres.
     */
    @Size(min = 1, max = 30, message = "O nome da categoria só pode ter entre 1 e 30 caracteres.")
    private String categoryName = null;

    /**
     * O preço do produto.
     *
     * @Size(min=0.0) O preço do produto não pode ser menor que "0.0".
     */
    @DecimalMin(value = "0.0", message = "O preço do produto não pode ser menor que \"0.0\".")
    private Double price = null;

    /**
     * A quantidade do produto em estoque.
     *
     * @Size(min=0) A quantidade do produto em estoque não pode ser menor que 0.
     */
    @Min(value = 0, message = "A quantidade do produto em estoque não pode ser menor que 0.")
    private Integer amountStock = null;
}
