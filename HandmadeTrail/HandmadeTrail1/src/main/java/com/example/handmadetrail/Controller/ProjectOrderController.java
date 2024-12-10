package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Servise.ProjectOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/projectOrder")
@RequiredArgsConstructor
public class ProjectOrderController {
    // 1. Declare a dependency for ProjectOrderService using Dependency Injection
    private final ProjectOrderService projectOrderService;

    // 2. CRUD
    // Get
    @GetMapping("/get")
    public ResponseEntity getAllProjectOrders() {
        return ResponseEntity.status(200).body(projectOrderService.getAllProjectOrders());
    }

    // 3. Extra endpoint:
    // Buy projects
    @PutMapping("/buyProjects/{diyBeginnerId}/{projectIds}")
    public ResponseEntity buyProjects(@PathVariable Integer diyBeginnerId, @PathVariable List<Integer> projectIds) {
        projectOrderService.buyProjects(diyBeginnerId, projectIds);
        return ResponseEntity.status(200).body(new ApiResponse("Purchase Completed Successfully."));
    }
}