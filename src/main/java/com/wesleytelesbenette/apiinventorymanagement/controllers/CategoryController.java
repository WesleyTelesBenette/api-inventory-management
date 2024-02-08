package com.wesleytelesbenette.apiinventorymanagement.controllers;

import com.wesleytelesbenette.apiinventorymanagement.dtos.CategoryUpdateDto;
import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import com.wesleytelesbenette.apiinventorymanagement.repositories.CategoryRepository;
import com.wesleytelesbenette.apiinventorymanagement.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
                : ResponseEntity.ok(categoryRepository.findAllByOrderByNameAsc());
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

    @PostMapping("/{newCategory}")
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

                        return ResponseEntity.ok(categoryUpdate);
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na atualização da categoria: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id)
    {
        try
        {
            if (productRepository.findByCategoryId(id).isPresent())
                return ResponseEntity.badRequest().build();

            return categoryRepository.findById(id)
                .map(categoryFound ->
                    {
                        categoryRepository.deleteById(id);
                        return ResponseEntity.ok(categoryFound);
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (Exception e)
        {
            System.err.println("Ops... ocorreu um erro na exclusão da categoria: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
