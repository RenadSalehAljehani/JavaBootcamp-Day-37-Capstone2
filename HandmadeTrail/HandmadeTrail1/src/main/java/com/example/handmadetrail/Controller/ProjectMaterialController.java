package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.ProjectMaterial;
import com.example.handmadetrail.Servise.ProjectMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projectMaterial")
@RequiredArgsConstructor
public class ProjectMaterialController {
    // 1. Declare a dependency for ProjectMaterialService using Dependency Injection
    private final ProjectMaterialService projectMaterialService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllProjectMaterials() {
        return ResponseEntity.status(200).body(projectMaterialService.getAllProjectMaterials());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addProjectMaterial(@RequestBody @Valid ProjectMaterial projectMaterial) {
        projectMaterialService.addProjectMaterial(projectMaterial);
        return ResponseEntity.status(200).body(new ApiResponse("New Project Material Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{projectMaterialId}")
    public ResponseEntity updateProjectMaterial(@PathVariable Integer projectMaterialId, @RequestBody @Valid ProjectMaterial projectMaterial) {
        projectMaterialService.updateProjectMaterial(projectMaterialId, projectMaterial);
        return ResponseEntity.status(200).body(new ApiResponse("Project Material Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{projectMaterialId}")
    public ResponseEntity deleteProjectMaterial(@PathVariable Integer projectMaterialId) {
        projectMaterialService.deleteProjectMaterial(projectMaterialId);
        return ResponseEntity.status(200).body(new ApiResponse("Project Material Deleted."));
    }
}