package com.wesleytelesbenette.apiinventorymanagement.dtos;

import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductDto
{
    /**
     * O identificador único do produto.
     */
    private Long id;

    /**
     * O nome do produto.
     *
     * @Size(min=1,max=30) O nome do produto só pode ter entre 1 e 30 caracteres.
     */
    @Size(min = 1, max = 30, message = "O nome do produto só pode ter entre 1 e 30 caracteres.")
    private String name;

    /**
     * A descrição do produto.
     */
    private String description;

    /**
     * Categoria do produto.
     */
    private Category category;

    /**
     * O nome da categoria do produto.
     *
     * @Size(min=1,max=30) O nome da categoria só pode ter entre 1 e 30 caracteres.
     */
    @Size(min = 1, max = 30, message = "O nome da categoria só pode ter entre 1 e 30 caracteres.")
    private String categoryName;

    /**
     * O preço do produto.
     *
     * @Size(min=0.0) O preço do produto não pode ser menor que "0.0".
     */
    @DecimalMin(value = "0.0", message = "O preço do produto não pode ser menor que \"0.0\".")
    private Double price;

    /**
     * A quantidade do produto em estoque.
     *
     * @Size(min=0) A quantidade do produto em estoque não pode ser menor que 0.
     */
    @Min(value = 0, message = "A quantidade do produto em estoque não pode ser menor que 0.")
    private Integer amountStock;
}
