package com.wesleytelesbenette.apiinventorymanagement.repositories;

import com.wesleytelesbenette.apiinventorymanagement.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>
{

    @Query("SELECT c FROM Category c ORDER BY c.id")
    List<Category> findAll();

    Category findByName(String name);

    Optional<Category> findByNameOp(String name);

    boolean existsByName(String name);
}
