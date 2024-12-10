package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Servise.OngoingProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ongoingProject")
@RequiredArgsConstructor
public class OngoingProjectController {
    // 1. Declare a dependency for OngoingProjectService using Dependency Injection
    private final OngoingProjectService ongoingProjectService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllOngoingProjects(){
        return ResponseEntity.status(200).body(ongoingProjectService.getAllOngoingProjects());
    }

    // 2.2 Delete
    @DeleteMapping("/delete/{ongoingProjectId}")
    public ResponseEntity deleteOngoingProject(@PathVariable Integer ongoingProjectId) {
        ongoingProjectService.deleteOngoingProject(ongoingProjectId);
        return ResponseEntity.status(200).body(new ApiResponse("Ongoing Project Deleted."));
    }
}