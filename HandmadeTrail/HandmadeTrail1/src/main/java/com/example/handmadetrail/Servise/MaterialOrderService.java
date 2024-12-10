package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.*;
import com.example.handmadetrail.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialOrderService {
    // 1. Declare a dependency for MaterialOrderRepository using Dependency Injection
    private final MaterialOrderRepository materialOrderRepository;

    // 2. Declare a dependency for DiyBeginnerRepository using Dependency Injection
    private final DiyBeginnerRepository diyBeginnerRepository;

    // 3. Declare a dependency for materialRepository using Dependency Injection
    private final MaterialRepository materialRepository;

    // 4. Declare a dependency for ProjectRepository using Dependency Injection
    private final ProjectRepository projectRepository;

    // 5. Declare a dependency for MaterialOrderItemRepository using Dependency Injection
    private final MaterialOrderItemRepository materialOrderItemRepository;

    // 6. Declare a dependency for ProjectMaterialRepository using Dependency Injection
    private final ProjectMaterialRepository projectMaterialRepository;

    // 7. CRUD
    // Get
    public List<MaterialOrder> getAllMaterialOrders() {
        return materialOrderRepository.findAll();
    }

    // 8. Extra endpoints
    // Endpoint to allow DIY beginner to buy materials for a project
    public void buyMaterials(Integer diyBeginnerId, Integer projectId) {
        // 1. Validate the DIY beginner and project
        DiyBeginner diyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);
        Project project = projectRepository.findProjectByProjectId(projectId);
        if (diyBeginner == null && project == null) {
            throw new ApiException("Diy Beginner and Project Not Found.");
        }
        if (diyBeginner == null) {
            throw new ApiException("Diy Beginner Not Found.");
        }
        if (project == null) {
            throw new ApiException("Diy Project Not Found.");
        }

        // 2. Initialize total cost and a list to hold order items
        double totalCost = 0.0;
        List<MaterialOrderItem> orderItems = new ArrayList<>();

        // 3. Get all materials for the project
        List<ProjectMaterial> projectMaterials = projectMaterialRepository.findProjectMaterialsByProjectId(projectId);

        // 4. Validate materials and prepare order items
        for (ProjectMaterial projectMaterial : projectMaterials) {
            Material material = materialRepository.findMaterialByMaterialId(projectMaterial.getMaterialId());
            if (material == null) {
                throw new ApiException("Material with Id (" + projectMaterial.getMaterialId() + ") Not Found.");
            }

            // Check material stock
            if (material.getStock() == 0) {
                throw new ApiException("Material with Id (" + projectMaterial.getMaterialId() + ") is Out Of Stock.");
            }

            // Add the material's price to the total cost
            totalCost = totalCost + material.getPrice();

            // Create an order item for this material
            MaterialOrderItem orderItem = new MaterialOrderItem();
            orderItem.setMaterialId(projectMaterial.getMaterialId());
            orderItem.setQuantity(projectMaterial.getQuantity());
            orderItem.setPrice(material.getPrice());
            orderItems.add(orderItem);
        }

        // 5. Check if the DIY beginner has enough balance
        if (diyBeginner.getBalance() < totalCost) {
            throw new ApiException("Insufficient Balance. Total cost is: " + totalCost + ", but available balance is: " + diyBeginner.getBalance());
        }

        // 6. Deduct total cost from the DIY beginner's balance
        diyBeginner.setBalance(diyBeginner.getBalance() - totalCost);
        diyBeginnerRepository.save(diyBeginner);

        // 7. Create and save the material order
        MaterialOrder materialOrder = new MaterialOrder();
        materialOrder.setDiyBeginnerId(diyBeginnerId);
        materialOrder.setTotalPrice(totalCost);
        materialOrderRepository.save(materialOrder);

        // 8. Associate the order items with the created material order
        for (MaterialOrderItem orderItem : orderItems) {
            // Associate with the created MaterialOrder
            orderItem.setMaterialOrderId(materialOrder.getMaterialOrderId());

            // Reduce stock for each material
            Material material = materialRepository.findMaterialByMaterialId(orderItem.getMaterialId());
            material.setStock(material.getStock() - 1);
            materialRepository.save(material);
        }

        // 9. Save all order items
        materialOrderItemRepository.saveAll(orderItems);
    }
}