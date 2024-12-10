package com.example.handmadetrail.Servise;

import com.example.handmadetrail.Model.ProjectOrderItem;
import com.example.handmadetrail.Repository.ProjectOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectOrderItemService {
    // 1. Declare a dependency for ProjectOrderItemRepository using Dependency Injection
    private final ProjectOrderItemRepository projectOrderItemRepository;

    // 4. CRUD
    // Get
    public List<ProjectOrderItem> getAllProjectOrderItems() {
        return projectOrderItemRepository.findAll();
    }
}