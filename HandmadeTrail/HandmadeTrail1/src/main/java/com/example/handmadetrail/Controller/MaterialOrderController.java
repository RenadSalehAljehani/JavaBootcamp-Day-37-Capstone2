package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Servise.MaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/materialOrder")
@RequiredArgsConstructor
public class MaterialOrderController {
    // 1. Declare a dependency for MaterialOrderService using Dependency Injection
    private final MaterialOrderService materialOrderService;

    // 2. CRUD
    // Get
    @GetMapping("/get")
    public ResponseEntity getAllMaterialOrders() {
        return ResponseEntity.status(200).body(materialOrderService.getAllMaterialOrders());
    }

    // 3. Extra endpoint:
    // Buy materials for a project
    @PutMapping("/buyMaterials/{diyBeginnerId}/{projectId}")
    public ResponseEntity buyMaterials(@PathVariable Integer diyBeginnerId, @PathVariable Integer projectId) {
        materialOrderService.buyMaterials(diyBeginnerId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Purchase Completed Successfully."));
    }
}