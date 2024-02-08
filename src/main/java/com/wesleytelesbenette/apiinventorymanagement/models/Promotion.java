package com.wesleytelesbenette.apiinventorymanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString //Debug
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TB_PROMOTIONS")
public class Promotion
{
    /**
     * O identificador único da promoção.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O produto que está em promoção.
     *
     * @NotNull O produto da promoção não pode ser nulo
     */
    @NotNull(message = "O produto da promoção não pode ser nulo.")
    @OneToOne
    @JoinColumn(name = "productId")
    private Product productId;

    /**
     * A porcentagem de desconto da promoção.
     *
     * @NotNull A porcentagem da promoção não pode ser nula.
     * @Size(min=0.1,max=100.0) A porcentagem da promoção deve ser entre "0.1" e "100.0".
     */
    @NotNull(message = "A porcentagem da promoção não pode ser nula.")
    @DecimalMin(value = "0.1", message = "A porcentagem da promoção não pode ser menor que \"0.1\".")
    @DecimalMax(value = "100.0", message = "A porcentagem da promoção não pode ser maior que \"100.0\".")
    @Column(name = "percentage")
    private Double percentage;

    /**
     * Construtor baseado num Prodcut e numa porcentagem.
     *
     * @param percentage Porcentagem de desconto do produto.
     * @Param product Produto (Product) que vai estar em promoção.
     */
    public Promotion(Double percentage, Product product)
    {
        this.percentage = percentage;
        this.productId = product;
    }
}
