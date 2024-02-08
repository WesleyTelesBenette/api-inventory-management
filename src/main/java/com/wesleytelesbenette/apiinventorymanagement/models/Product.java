package com.wesleytelesbenette.apiinventorymanagement.models;

import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductCreateDto;
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
     * @Size(min=1,max=50) O nome do produto só pode ter entre 1 e 50 caracteres.
     */
    @NotBlank(message = "O nome do produto não pode estar em branco.")
    @Size(min = 1, max = 50, message = "O nome do produto só pode ter entre 1 e 50 caracteres.")
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
    @JoinColumn(name = "categoryId")
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
    @Column(name = "amountStock")
    private Integer amountStock;

    /**
     * Construtor baseado num Prodcut para requisições gerais.
     *
     * @param newProduct Objeto Product com os dados do produto.
     */
    public Product(Product newProduct)
    {
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.category = newProduct.getCategory();
        this.price = newProduct.getPrice();
        this.amountStock = newProduct.getAmountStock();
    }

    /**
     * Construtor baseado num DTO para requisições POST.
     *
     * @param newProduct Objeto ProductCreateDto com os dados do produto.
     */
    public Product(ProductCreateDto newProduct)
    {
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.price = newProduct.getPrice();
        this.amountStock = newProduct.getAmountStock();
    }
}
