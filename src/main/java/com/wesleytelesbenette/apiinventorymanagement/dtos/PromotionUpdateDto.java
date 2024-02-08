package com.wesleytelesbenette.apiinventorymanagement.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PromotionUpdateDto
{
    /**
     * O identificador único da promoção.
     *
     * @NotNull A promoção não pode ser nula.
     */
    @NotNull(message = "A promoção não pode ser nula.")
    private Long id;

    /**
     * O produto que está em promoção.
     */
    private Long productId = null;

    /**
     * A porcentagem de desconto da promoção.
     *
     * @Size(min=0.1,max=100.0) A porcentagem da promoção deve ser entre "0.1" e "100.0".
     */
    @DecimalMin(value = "0.1", message = "A porcentagem da promoção não pode ser menor que \"0.1\".")
    @DecimalMax(value = "100.0", message = "A porcentagem da promoção não pode ser maior que \"100.0\".")
    private Double percentage = null;
}
