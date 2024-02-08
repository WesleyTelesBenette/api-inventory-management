package com.wesleytelesbenette.apiinventorymanagement.controllers;

import com.wesleytelesbenette.apiinventorymanagement.dtos.PromotionCreateDto;
import com.wesleytelesbenette.apiinventorymanagement.dtos.PromotionUpdateDto;
import com.wesleytelesbenette.apiinventorymanagement.models.Promotion;
import com.wesleytelesbenette.apiinventorymanagement.repositories.ProductRepository;
import com.wesleytelesbenette.apiinventorymanagement.repositories.PromotionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionController
{
    PromotionRepository promotionRepository;
    ProductRepository productRepository;

    @Autowired
    public PromotionController(PromotionRepository repM, ProductRepository repP)
    {
        promotionRepository = repM;
        productRepository = repP;
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getPromotions()
    {
        try
        {
            return (promotionRepository.count() > 0)
                ? ResponseEntity.ok(promotionRepository.findAllByOrderByIdAsc())
                : ResponseEntity.noContent().build();
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta das promoções: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/promotion/{id}")
    public ResponseEntity<Promotion> getPromotionId(@PathVariable Long id)
    {
        try
        {
            return promotionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta da promoção: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/percentage/{id}")
    public ResponseEntity<Double> getPromotionPercentage(@PathVariable Long id)
    {
        try
        {
            return promotionRepository.findByPromotion(id)
                .map(promotionFound -> ResponseEntity.ok(promotionFound.getPercentage()))
                .orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta da promoção: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/value/{id}")
    public ResponseEntity<Double> getPromotionValue(@PathVariable Long id)
    {
        try
        {
            return promotionRepository.findByPromotion(id)
                .map(promotionFound ->
                    {
                        Double
                            price = promotionFound.getProductId().getPrice(),
                            percentage = promotionFound.getPercentage(),
                            responseValue = price - (( price * percentage ) / 100);

                        return ResponseEntity.ok(responseValue);
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta da promoção: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody @Valid PromotionCreateDto dto)
    {
        try
        {
            if (promotionRepository.findByPromotion(dto.getProductId()).isPresent())
                return ResponseEntity.badRequest().build();

            return productRepository.findById(dto.getProductId())
                .map(productFound ->
                    {
                        Promotion newPromotion = new Promotion(dto.getPercentage(), productFound);
                        promotionRepository.save(newPromotion);

                        return ResponseEntity
                                .created(URI.create("/promotion/" + newPromotion.getId()))
                                .body(newPromotion);
                    }
                ).orElseGet(() -> ResponseEntity.noContent().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na criação da promoção: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<Promotion> updatePromotion(@RequestBody @Valid PromotionUpdateDto dto)
    {
        try
        {
            if (!promotionRepository.existsById(dto.getId()))
                return ResponseEntity.badRequest().build();

            return promotionRepository.findById(dto.getId())
                .map(promotionFound ->
                    {
                        promotionFound.setPercentage(
                            (dto.getPercentage() != null)
                                ? dto.getPercentage()
                                : promotionFound.getPercentage()
                        );
                        promotionFound.setProductId(
                            (dto.getProductId() != null)
                                ? productRepository.findById(dto.getProductId()).get()
                                : promotionFound.getProductId()
                        );
                        promotionRepository.save(promotionFound);

                        return ResponseEntity.ok(promotionFound);
                    }
                ).orElseGet(() -> ResponseEntity.noContent().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na criação da promoção: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    //DELETE Promotion
    @DeleteMapping("/{id}")
    public ResponseEntity<Promotion> deletePromotion(@PathVariable Long id)
    {
        try
        {
            return promotionRepository.findById(id)
                .map(promotionFound ->
                    {
                        promotionRepository.deleteById(id);
                        return ResponseEntity.ok(promotionFound);
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na exclusão da promoção: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
