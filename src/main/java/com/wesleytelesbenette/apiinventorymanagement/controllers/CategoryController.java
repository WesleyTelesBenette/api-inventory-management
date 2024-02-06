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

import java.util.Comparator;
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
            List<Category> responseList = categoryRepository.findAll();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{category}")
    public ResponseEntity<Category> createCategory(@PathVariable String category)
    {
        try
        {
            if (categoryRepository.findByName(category) != null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Category responseCategory = categoryRepository.save(new Category(category));
            return new ResponseEntity<>(responseCategory, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid CategoryUpdateDto dto)
    {
        try
        {
            if (categoryRepository.findByName(dto.getOldCategory()) == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if (categoryRepository.findByName(dto.getNewCategory()) != null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Category category = categoryRepository.findByName(dto.getOldCategory());
            category.setName(dto.getNewCategory());
            categoryRepository.save(category);

            List<Product> productsWithCategory = productRepository.findByCategory(category);
            for (Product prod : productsWithCategory) {
                prod.setCategory(category);
            }
            productRepository.saveAll(productsWithCategory);

            return new ResponseEntity<>(category, HttpStatus.OK);
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
