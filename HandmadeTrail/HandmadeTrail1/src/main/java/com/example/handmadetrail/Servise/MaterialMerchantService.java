package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.MaterialMerchant;
import com.example.handmadetrail.Repository.MaterialMerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialMerchantService {
    // 1. Declare a dependency for MaterialMerchantRepository using Dependency Injection
    private final MaterialMerchantRepository materialMerchantRepository;

    // 2.CRUD
    // 2.1 Get
    public List<MaterialMerchant> getAllMaterialMerchants() {
        return materialMerchantRepository.findAll();
    }

    // 2.2 Post
    public void addMaterialMerchant(MaterialMerchant materialMerchant) {
        materialMerchantRepository.save(materialMerchant);
    }

    // 2.3 Update
    public void updateMaterialMerchant(Integer materialMerchantId, MaterialMerchant materialMerchant) {
        // 1. Check if material merchant to be updated exists
        MaterialMerchant oldMaterialMerchant = materialMerchantRepository.findMaterialMerchantByMaterialMerchantId(materialMerchantId);
        if (oldMaterialMerchant == null) {
            throw new ApiException("Material Merchant Not Found.");
        }
        // 2. Set new values
        oldMaterialMerchant.setName(materialMerchant.getName());
        // 3. Save changes
        materialMerchantRepository.save(oldMaterialMerchant);
    }

    // 2.4 Delete
    public void deleteMaterialMerchant(Integer materialMerchantId) {
        // Check if material merchant to be deleted exists
        MaterialMerchant oldMaterialMerchant = materialMerchantRepository.findMaterialMerchantByMaterialMerchantId(materialMerchantId);
        if (oldMaterialMerchant == null) {
            throw new ApiException("Material Merchant Not Found.");
        }
        materialMerchantRepository.delete(oldMaterialMerchant);
    }
}