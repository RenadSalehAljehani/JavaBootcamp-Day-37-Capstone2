package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.MaterialOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialOrderItemRepository extends JpaRepository<MaterialOrderItem, Integer> {
}