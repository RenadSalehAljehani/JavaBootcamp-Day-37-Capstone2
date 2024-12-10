package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.Material;
import com.example.handmadetrail.Servise.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialController {
    // 1. Declare a dependency for MaterialService using Dependency Injection
    private final MaterialService materialService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllMaterials() {
        return ResponseEntity.status(200).body(materialService.getAllMaterials());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addMaterial(@RequestBody @Valid Material material) {
        materialService.addMaterial(material);
        return ResponseEntity.status(200).body(new ApiResponse("New Material Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{materialId}")
    public ResponseEntity updateMaterial(@PathVariable Integer materialId, @RequestBody @Valid Material material) {
        materialService.updateMaterial(materialId, material);
        return ResponseEntity.status(200).body(new ApiResponse("Material Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{materialId}")
    public ResponseEntity deleteMaterial(@PathVariable Integer materialId) {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.status(200).body(new ApiResponse("Material Deleted."));
    }

    // 3.Extra endpoint
    // 3.1 Add more material stock
    @PutMapping("/addStock/{materialMerchantId}/{materialId}/{amount}")
    public ResponseEntity addStock(@PathVariable Integer materialMerchantId, @PathVariable Integer materialId, @PathVariable Integer amount) {
        materialService.addStock(materialMerchantId, materialId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("New Material Stock Added."));
    }

    // 3.2 An endpoint to get all materials by a price range
    @GetMapping("/getAllMaterialsByPriceRange/{minPrice}/{maxPrice}")
    public ResponseEntity getAllMaterialsByPriceRange(@PathVariable Integer minPrice, @PathVariable Integer maxPrice) {
        List<Material> materials = materialService.getAllMaterialsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.status(200).body(materials);
    }
}