package com.wesleytelesbenette.apiinventorymanagement.controllers;

import com.wesleytelesbenette.apiinventorymanagement.dtos.CategoryUpdateDto;
import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import com.wesleytelesbenette.apiinventorymanagement.models.Product;
import com.wesleytelesbenette.apiinventorymanagement.repositories.CategoryRepository;
import com.wesleytelesbenette.apiinventorymanagement.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/category")
public class CategoryController
{
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Autowired
    public CategoryController(CategoryRepository repC, ProductRepository repP)
    {
        categoryRepository = repC;
        productRepository = repP;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories()
    {
        try
        {
            return (categoryRepository.count() == 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categoryRepository.findAll());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta das categorias: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryId(@PathVariable Long id)
    {
        try
        {
            return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na consulta da categoria: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{category}")
    public ResponseEntity<Category> createCategory(@PathVariable String newCategory)
    {
        try
        {
            if (categoryRepository.existsByName(newCategory))
                return ResponseEntity.badRequest().build();

            Category saveCategory = categoryRepository.save(new Category(newCategory));
            return ResponseEntity
                    .created(URI.create("/category/" + saveCategory.getId()))
                    .body(saveCategory);
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na criação da categoria: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid CategoryUpdateDto dto)
    {
        try
        {
            if (categoryRepository.existsByName(dto.getNewCategory()))
                return ResponseEntity.badRequest().build(); //Already exists

            return categoryRepository.findByNameOp(dto.getOldCategory())
                .map(categoryUpdate ->
                    {
                        categoryUpdate.setName(dto.getNewCategory());
                        categoryRepository.save(categoryUpdate);

                        List<Product> productsWithCategory = productRepository.findByCategory(categoryUpdate);
                        for (Product prod : productsWithCategory) prod.setCategory(categoryUpdate);
                        productRepository.saveAll(productsWithCategory);

                        return ResponseEntity.ok(categoryUpdate);
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id)
    {
        try
        {
            if (categoryRepository.findById(id).isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
