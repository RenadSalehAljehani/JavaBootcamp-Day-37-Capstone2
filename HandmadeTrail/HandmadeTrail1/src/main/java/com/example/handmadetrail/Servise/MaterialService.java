package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.Material;
import com.example.handmadetrail.Model.MaterialMerchant;
import com.example.handmadetrail.Repository.MaterialMerchantRepository;
import com.example.handmadetrail.Repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    // 1. Declare a dependency for MaterialRepository using Dependency Injection
    private final MaterialRepository materialRepository;

    // 2. Declare a dependency for MaterialMerchantRepository using Dependency Injection
    private final MaterialMerchantRepository materialMerchantRepository;

    // 3. CRUD
    // 3.1 Get
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    // 3.2 Post
    public void addMaterial(Material material) {
        // Check the existence of material merchant id
        boolean invalidMaterialMerchantId = materialMerchantRepository.findMaterialMerchantByMaterialMerchantId(material.getMaterialMerchantId()) == null;

        if (invalidMaterialMerchantId) {
            throw new ApiException("Material Merchant Not Found.");
        }

        materialRepository.save(material);
    }

    // 2.3 Update
    public void updateMaterial(Integer materialId, Material material) {
        // 1. Check if material to be updated exists
        Material oldMaterial = materialRepository.findMaterialByMaterialId(materialId);
        if (oldMaterial == null) {
            throw new ApiException("Material Not Found.");
        }

        // 2. Check the existence of material merchant id
        boolean invalidMaterialMerchantId = materialMerchantRepository.findMaterialMerchantByMaterialMerchantId(material.getMaterialMerchantId()) == null;

        if (invalidMaterialMerchantId) {
            throw new ApiException("Material Merchant Not Found.");
        }

        // 3. Set new values
        oldMaterial.setMaterialMerchantId(material.getMaterialMerchantId());
        oldMaterial.setName(material.getName());
        oldMaterial.setPrice(material.getPrice());
        oldMaterial.setStock(material.getStock());

        // 4. Save changes
        materialRepository.save(oldMaterial);
    }

    // 2.4 Delete
    public void deleteMaterial(Integer materialId) {
        // Check if material to be deleted exists
        Material oldMaterial = materialRepository.findMaterialByMaterialId(materialId);
        if (oldMaterial == null) {
            throw new ApiException("Material Not Found.");
        }
        materialRepository.delete(oldMaterial);
    }

    // 3.Extra endpoint
    // An endpoint to get all materials by a price range
    public List<Material> getAllMaterialsByPriceRange(Integer minPrice, Integer maxPrice) {
        List<Material> materials = materialRepository.getAllMaterialsByPriceRange(minPrice, maxPrice);

        if (materials.isEmpty()) {
            throw new ApiException("No Materials Within Specified Price Range Has Been Found.");
        }
        return materials;
    }

    // 5. Extra endpoints
    // An endpoint to allow merchant to add more material stock
    public void addStock(Integer materialMerchantId, Integer materialId, Integer amount) {
        // 1. Check if amount is valid
        if (amount <= 0) {
            throw new ApiException("Material Amount Should Be a Positive Number Larger Than Zero.");
        }

        // 2. Check the existence of material merchant id and material id
        MaterialMerchant materialMerchant = materialMerchantRepository.findMaterialMerchantByMaterialMerchantId(materialMerchantId);
        Material material = materialRepository.findMaterialByMaterialId(materialId);

        if (materialMerchant == null && material == null) {
            throw new ApiException("Material Merchant and Material Not Found.");
        }
        if (materialMerchant == null) {
            throw new ApiException("Material Merchant Not Found.");
        }
        if (material == null) {
            throw new ApiException("Material Not Found.");
        }

        // 4. Add stock
        material.setStock(material.getStock() + amount);

        // 5. Save the changes
        materialRepository.save(material);
    }
}