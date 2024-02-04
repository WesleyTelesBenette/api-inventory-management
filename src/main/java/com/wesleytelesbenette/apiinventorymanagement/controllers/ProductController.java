package com.wesleytelesbenette.apiinventorymanagement.controllers;

import com.wesleytelesbenette.apiinventorymanagement.models.Product;
import com.wesleytelesbenette.apiinventorymanagement.repositories.ProductRepository;
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

    @Autowired
    public ProductController(ProductRepository rep)
    {
        this.productRepository = rep;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts()
    {
        List<Product> responseList = productRepository.findAllByOrderByNameAsc();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductId(@PathVariable Long id)
    {
        Optional<Product> optionalProduct = productRepository.findById(id);
        boolean query = optionalProduct.isPresent();

        Product responseProduct = (query) ? optionalProduct.get() : null;
        HttpStatus responseHttp = (query) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(responseProduct, responseHttp);
    }

    //@PostMapping("/")
    public ResponseEntity<String> createProduct()
    {
        //Em produção...
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductId(@PathVariable Long id)
    {
        String responseMessage = "O id fornecido não pertence a nenhum produto.";
        HttpStatus responseHttp = HttpStatus.NOT_FOUND;

        if(productRepository.findById(id).isPresent())
        {
            productRepository.deleteById(id);
            boolean productDeleteFailed = productRepository.findById(id).isPresent();

            responseMessage = (productDeleteFailed) ? "Algo deu errado..." : "Produto deletado com sucesso!";
            responseHttp = (productDeleteFailed) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        }

        return new ResponseEntity<>(responseMessage, responseHttp);
    }
}
