package com.example.handmadetrail.Servise;

import com.example.handmadetrail.Model.MaterialOrderItem;
import com.example.handmadetrail.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialOrderItemService {
    // 1. Declare a dependency for MaterialOrderRepository using Dependency Injection
    private final MaterialOrderItemRepository materialOrderItemRepository;

    // 4. CRUD
    // Get
    public List<MaterialOrderItem> getAllMaterialOrderItems() {
        return materialOrderItemRepository.findAll();
    }
}