package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.MaterialMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialMerchantRepository extends JpaRepository<MaterialMerchant, Integer> {
    // Using find
    MaterialMerchant findMaterialMerchantByMaterialMerchantId(Integer materialMerchantId);
}