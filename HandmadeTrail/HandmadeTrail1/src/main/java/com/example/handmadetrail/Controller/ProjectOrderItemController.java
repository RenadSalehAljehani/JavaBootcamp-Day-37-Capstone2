package com.example.handmadetrail.Controller;

import com.example.handmadetrail.Servise.ProjectOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projectOrderItem")
@RequiredArgsConstructor
public class ProjectOrderItemController {
    // 1. Declare a dependency for ProjectOrderItemService using Dependency Injection
    private final ProjectOrderItemService projectOrderItemService;

    // 2. CRUD
    // Get
    @GetMapping("/get")
    public ResponseEntity getAllProjectOrderItems() {
        return ResponseEntity.status(200).body(projectOrderItemService.getAllProjectOrderItems());
    }
}