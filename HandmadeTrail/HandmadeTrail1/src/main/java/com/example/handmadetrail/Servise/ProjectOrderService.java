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
public class ProjectOrderService {
    // 1. Declare a dependency for ProjectOrderRepository using Dependency Injection
    private final ProjectOrderRepository projectOrderRepository;

    // 2. Declare a dependency for ProjectOrderItemRepository using Dependency Injection
    private final ProjectOrderItemRepository projectOrderItemRepository;

    // 3. Declare a dependency for DiyBeginnerRepository using Dependency Injection
    private final DiyBeginnerRepository diyBeginnerRepository;

    // 4. Declare a dependency for ProjectRepository using Dependency Injection
    private final ProjectRepository projectRepository;

    // 5. CRUD
    // Get
    public List<ProjectOrder> getAllProjectOrders() {
        return projectOrderRepository.findAll();
    }

    // 6. Extra endpoints
    // Endpoint to allow DIY beginner to buy projects
    public void buyProjects(Integer diyBeginnerId, List<Integer> projectIds) {
        // 1. Validate the DIY beginner
        DiyBeginner diyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);
        if (diyBeginner == null) {
            throw new ApiException("Diy Beginner Id Not Found.");
        }

        // 2. Initialize total cost and a list to hold order items
        double totalCost = 0.0;
        List<ProjectOrderItem> orderItems = new ArrayList<>();

        // 3. Validate projects and prepare order items
        for (Integer projectId : projectIds) {
            Project project = projectRepository.findProjectByProjectId(projectId);
            if (project == null) {
                throw new ApiException("Project with Id (" + projectId + ") Not Found.");
            }

            // 4. Check project stock
            if (project.getStock() == 0) {
                throw new ApiException("Project with Id (" + projectId + ") is Out Of Stock.");
            }

            // Add the project's price to the total cost
            totalCost = totalCost + project.getPrice();

            // Create an order item for this project
            ProjectOrderItem orderItem = new ProjectOrderItem();
            orderItem.setProjectId(projectId);
            orderItem.setQuantity(1); // Assuming quantity is always 1 for simplicity
            orderItem.setPrice(project.getPrice());
            orderItems.add(orderItem);
        }

        // 4. Check if the DIY beginner has enough balance
        if (diyBeginner.getBalance() < totalCost) {
            throw new ApiException("Insufficient Balance. Total cost is: " + totalCost + ", but available balance is: " + diyBeginner.getBalance());
        }

        // 5. Deduct total cost from the DIY beginner's balance
        diyBeginner.setBalance(diyBeginner.getBalance() - totalCost);
        diyBeginnerRepository.save(diyBeginner);

        // 6. Create and save the project order
        ProjectOrder projectOrder = new ProjectOrder();
        projectOrder.setDiyBeginnerId(diyBeginnerId);
        projectOrder.setTotalPrice(totalCost);
        projectOrderRepository.save(projectOrder);

        // 7. Associate the order items with the created project order
        for (ProjectOrderItem orderItem : orderItems) {
            // Associate with the created ProjectOrder
            orderItem.setProjectOrderId(projectOrder.getProjectOrderId());

            // Reduce stock for each project
            Project project = projectRepository.findProjectByProjectId(orderItem.getProjectId());
            project.setStock(project.getStock() - 1);
            projectRepository.save(project);
        }

        // 8. Save all order items
        projectOrderItemRepository.saveAll(orderItems);
    }
}