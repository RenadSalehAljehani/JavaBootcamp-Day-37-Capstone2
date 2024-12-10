package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.ProjectOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectOrderItemRepository extends JpaRepository<ProjectOrderItem, Integer> {
}