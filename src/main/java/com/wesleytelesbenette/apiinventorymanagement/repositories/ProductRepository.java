package com.wesleytelesbenette.apiinventorymanagement.repositories;

import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import com.wesleytelesbenette.apiinventorymanagement.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    Optional<Product> findByCategoryId(@Param("id") Long id);

    List<Product> findAllByOrderByNameAsc();
}
