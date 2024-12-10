package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialOrderRepository extends JpaRepository<MaterialOrder, Integer> {
}