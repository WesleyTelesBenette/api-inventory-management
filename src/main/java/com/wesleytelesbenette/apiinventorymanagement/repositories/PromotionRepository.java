package com.wesleytelesbenette.apiinventorymanagement.repositories;

import com.wesleytelesbenette.apiinventorymanagement.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>
{
    List<Promotion> findAllByOrderByIdAsc();

    @Query("SELECT p FROM Promotion p WHERE p.productId.id = :id")
    Optional<Promotion> findByPromotion(@Param("id") Long id);
}
