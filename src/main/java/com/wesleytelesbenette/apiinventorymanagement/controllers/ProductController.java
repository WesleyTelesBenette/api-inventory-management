package com.wesleytelesbenette.apiinventorymanagement.controllers;

import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductCreateDto;
import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductUpdateDto;
import com.wesleytelesbenette.apiinventorymanagement.models.Product;
import com.wesleytelesbenette.apiinventorymanagement.repositories.CategoryRepository;
import com.wesleytelesbenette.apiinventorymanagement.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController
{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductRepository repP, CategoryRepository repC)
    {
        this.productRepository = repP;
        this.categoryRepository = repC;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts()
    {
        try
        {
            return (productRepository.count() == 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(productRepository.findAllByOrderByNameAsc());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta dos produtos: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductId(@PathVariable Long id)
    {
        try
        {
            return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta do produto: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductCreateDto dto)
    {
        try
        {
            return categoryRepository.findByNameOp(dto.getCategoryName())
                .map(categoryFound ->
                    {
                        Product newProduct = new Product(dto);
                        newProduct.setCategory(categoryFound);
                        Product saveProduct = productRepository.save(newProduct);

                        return ResponseEntity
                            .created(URI.create("/product/" + saveProduct.getId()))
                            .body(saveProduct);
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na criação do produto: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductUpdateDto dto)
    {
        try
        {
            return productRepository.findById(dto.getId())
                .map(productFound ->
                    {
                        Product newProduct = repairProductUpdate(dto);
                        return ResponseEntity.ok(productRepository.save(newProduct));
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na atualização do produto: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private Product repairProductUpdate(ProductUpdateDto dto)
    {
        Product productRepair = new Product();
        Long productId = dto.getId();

        productRepair.setId(productId);

        productRepair.setName(
            (dto.getName() != null)
                ? dto.getName()
                : productRepository.findById(productId).get().getName()
        );

        productRepair.setDescription(
            (dto.getDescription() != null)
                ? dto.getDescription()
                : productRepository.findById(productId).get().getDescription()
        );

        productRepair.setCategory(
            (dto.getCategoryName() != null)
                ? categoryRepository.findByName(dto.getCategoryName())
                : productRepository.findById(productId).get().getCategory()
        );

        productRepair.setPrice(
            (dto.getPrice() != null)
                ? dto.getPrice()
                : productRepository.findById(productId).get().getPrice()
        );

        productRepair.setAmountStock(
                (dto.getAmountStock() != null)
                        ? dto.getAmountStock()
                        : productRepository.findById(productId).get().getAmountStock()
        );

        return productRepair;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductId(@PathVariable Long id)
    {
        try
        {
            return productRepository.findById(id)
                .map(productFound ->
                    {
                        productRepository.delete(productFound);
                        return ResponseEntity.ok().build();
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na exclusão do produto: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
