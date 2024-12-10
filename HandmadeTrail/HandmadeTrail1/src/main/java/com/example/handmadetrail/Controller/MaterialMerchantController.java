package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.MaterialMerchant;
import com.example.handmadetrail.Servise.MaterialMerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/materialMerchant")
@RequiredArgsConstructor
public class MaterialMerchantController {

    // 1. Declare a dependency for MaterialMerchantService using Dependency Injection
    private final MaterialMerchantService materialMerchantService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllMaterialMerchants() {
        return ResponseEntity.status(200).body(materialMerchantService.getAllMaterialMerchants());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addMaterialMerchant(@RequestBody @Valid MaterialMerchant materialMerchant) {
        materialMerchantService.addMaterialMerchant(materialMerchant);
        return ResponseEntity.status(200).body(new ApiResponse("New Material Merchant Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{materialMerchantId}")
    public ResponseEntity updateMaterialMerchant(@PathVariable Integer materialMerchantId, @RequestBody @Valid MaterialMerchant materialMerchant) {
        materialMerchantService.updateMaterialMerchant(materialMerchantId, materialMerchant);
        return ResponseEntity.status(200).body(new ApiResponse("Material Merchant Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{materialMerchantId}")
    public ResponseEntity deleteMaterialMerchant(@PathVariable Integer materialMerchantId) {
        materialMerchantService.deleteMaterialMerchant(materialMerchantId);
        return ResponseEntity.status(200).body(new ApiResponse("Material Merchant Deleted."));
    }
}