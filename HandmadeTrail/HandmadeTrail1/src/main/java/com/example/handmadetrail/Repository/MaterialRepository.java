package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    // Using find
    Material findMaterialByMaterialId(Integer materialId);

    // Using JPQL
    @Query("select m from Material m where m.price >= ?1 and m.price <= ?2")
    List<Material> getAllMaterialsByPriceRange(Integer minPrice, Integer maxPrice);
}