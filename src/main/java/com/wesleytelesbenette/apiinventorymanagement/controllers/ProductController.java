package com.wesleytelesbenette.apiinventorymanagement.controllers;

import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductCreateDto;
import com.wesleytelesbenette.apiinventorymanagement.dtos.ProductUpdateDto;
import com.wesleytelesbenette.apiinventorymanagement.models.Product;
import com.wesleytelesbenette.apiinventorymanagement.repositories.CategoryRepository;
import com.wesleytelesbenette.apiinventorymanagement.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/product")
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

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts()
    {
        try
        {
            List<Product> responseList = productRepository.findAllByOrderByNameAsc();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductId(@PathVariable Long id)
    {
        try
        {
            Optional<Product> optionalProduct = productRepository.findById(id);
            boolean query = optionalProduct.isPresent();

            Product responseProduct = (query) ? optionalProduct.get() : null;
            HttpStatus responseHttp = (query) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

            return new ResponseEntity<>(responseProduct, responseHttp);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateDto dto)
    {
        try
        {
            Product responseProduct = productRepository.save(new Product(dto));
            HttpStatus responseHttp = (responseProduct != null) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

            return new ResponseEntity<>(responseProduct, responseHttp);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductUpdateDto dto)
    {
        try
        {
            Product responseProduct = null;
            HttpStatus responseHttp = HttpStatus.NOT_FOUND;

            if (productRepository.findById(dto.getId()) != null)
            {
                Product newProduct = repairProductUpdate(dto);
                responseProduct = productRepository.save(newProduct);
                responseHttp = (responseProduct != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            }

            return new ResponseEntity<>(responseProduct, responseHttp);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    public ResponseEntity<String> deleteProductId(@PathVariable Long id)
    {
        try
        {
            String responseMessage = "";
            HttpStatus responseHttp = HttpStatus.NOT_FOUND;

            if (productRepository.findById(id).isPresent()) {
                productRepository.deleteById(id);
                boolean productDeleteFailed = productRepository.findById(id).isPresent();

                responseMessage = (productDeleteFailed) ? "Algo deu errado..." : "Produto deletado com sucesso!";
                responseHttp = (productDeleteFailed) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            }

            return new ResponseEntity<>(responseMessage, responseHttp);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
