package com.wesleytelesbenette.apiinventorymanagement.repositories;

import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import com.wesleytelesbenette.apiinventorymanagement.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Optional<Product> findByOrderByIdAsc(Long id);

    List<Product> findAllByOrderByNameAsc();

    List<Product> findByCategory(Category category);
}
