package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.CompletedProject;
import com.example.handmadetrail.Servise.CompletedProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/completedProject")
@RequiredArgsConstructor
public class CompletedProjectController {
    // 1. Declare a dependency for CompletedProjectService using Dependency Injection
    private final CompletedProjectService completedProjectService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllCompletedProjects() {
        return ResponseEntity.status(200).body(completedProjectService.getAllCompletedProjects());
    }

    // 2.2 Delete
    @DeleteMapping("/delete/{completedProjectId}")
    public ResponseEntity deleteOngoingProject(@PathVariable Integer completedProjectId) {
        completedProjectService.deleteCompletedProject(completedProjectId);
        return ResponseEntity.status(200).body(new ApiResponse("Completed Project Deleted."));
    }

    // 3. Extra endpoint
    // An endpoint to get all completed projects for a DIY beginner by id
    @GetMapping("/getAllCompletedProjectsForABeginner/{diyBeginnerId}")
    public ResponseEntity getAllCompletedProjectsForABeginner(@PathVariable Integer diyBeginnerId) {
        List<CompletedProject> completedProjectList = completedProjectService.getAllCompletedProjectsForABeginner(diyBeginnerId);
        return ResponseEntity.status(200).body(completedProjectList);
    }
}