package com.example.handmadetrail.Controller;

import com.example.handmadetrail.Servise.MaterialOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/materialOrderItem")
@RequiredArgsConstructor
public class MaterialOrderItemController {
    // 1. Declare a dependency for MaterialOrderService using Dependency Injection
    private final MaterialOrderItemService materialOrderItemService;

    // 2. CRUD
    // Get
    @GetMapping("/get")
    public ResponseEntity getAllMaterialOrderItems() {
        return ResponseEntity.status(200).body(materialOrderItemService.getAllMaterialOrderItems());
    }
}