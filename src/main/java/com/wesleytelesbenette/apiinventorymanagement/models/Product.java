package com.wesleytelesbenette.apiinventorymanagement.models;

import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductCreateDto;
import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@ToString //Debug
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TB_PRODUCTS")
public class Product
{
    /**
     * O identificador único do produto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do produto.
     *
     * @NotEmpty O nome do produto não pode estar em branco.
     * @Size(min=1,max=30) O nome do produto só pode ter entre 1 e 30 caracteres.
     */
    @NotBlank(message = "O nome do produto não pode estar em branco.")
    @Size(min = 1, max = 30, message = "O nome do produto só pode ter entre 1 e 30 caracteres.")
    @Column(name = "name")
    private String name;

    /**
     * A descrição do produto.
     */
    @Column(name = "description")
    private String description;

    /**
     * A categoria do produto.
     *
     * @NotNull A categoria do produto não pode ser nula.
     */
    @NotNull(message = "A categoria do produto não pode ser nula.")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * O preço do produto.
     *
     * @NotNull O preço do produto não pode ser nulo.
     * @Size(min=0.0) O preço do produto não pode ser menor que "0.0".
     */
    @NotNull(message = "O preço do produto não pode ser nulo.")
    @DecimalMin(value = "0.0", message = "O preço do produto não pode ser menor que \"0.0\".")
    @Column(name = "price")
    private Double price;

    /**
     * A quantidade do produto em estoque.
     *
     * @NotNull A quantidade do produto em estoque não pode ser nula.
     * @Size(min=0) A quantidade do produto em estoque não pode ser menor que 0.
     */
    @NotNull(message = "A quantidade do produto em estoque não pode ser nula.")
    @Min(value = 0, message = "A quantidade do produto em estoque não pode ser menor que 0.")
    @Column(name = "amount_stock")
    private Integer amountStock;

    /**
     * Construtor baseado num DTO para requisições POST.
     *
     * @param newProduct Objeto ProductCreateDto com os dados do produto.
     */
    public Product(ProductCreateDto newProduct)
    {
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.category = newProduct.getCategory();
        this.price = newProduct.getPrice();
        this.amountStock = newProduct.getAmountStock();
    }

    /**
     * Construtor baseado num DTO para requisições PUT.
     *
     * @param newProduct Objeto ProductUpdateDto com os dados do produto.
     */
    public Product(ProductUpdateDto newProduct)
    {
        this.id = newProduct.getId();
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.category = newProduct.getCategory();
        this.price = newProduct.getPrice();
        this.amountStock = newProduct.getAmountStock();
    }
}
