package com.wesleytelesbenette.apiinventorymanagement.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PromotionCreateDto
{
    /**
     * O produto que está em promoção.
     *
     * @NotNull O produto da promoção não pode ser nulo
     */
    @NotNull(message = "O produto da promoção não pode ser nulo.")
    private Long productId;

    /**
     * A porcentagem de desconto da promoção.
     *
     * @NotNull A porcentagem da promoção não pode ser nula.
     * @Size(min=0.1,max=100.0) A porcentagem da promoção deve ser entre "0.1" e "100.0".
     */
    @NotNull(message = "A porcentagem da promoção não pode ser nula.")
    @DecimalMin(value = "0.1", message = "A porcentagem da promoção não pode ser menor que \"0.1\".")
    @DecimalMax(value = "100.0", message = "A porcentagem da promoção não pode ser maior que \"100.0\".")
    private Double percentage;
}
