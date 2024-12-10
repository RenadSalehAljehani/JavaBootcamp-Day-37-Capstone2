package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.Project;
import com.example.handmadetrail.Servise.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    // 1. Declare a dependency for ProjectService using Dependency Injection
    private final ProjectService projectService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllProjects() {
        return ResponseEntity.status(200).body(projectService.getAllProjects());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addProject(@RequestBody @Valid Project project) {
        projectService.addProject(project);
        return ResponseEntity.status(200).body(new ApiResponse("New Project Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{projectId}")
    public ResponseEntity updateProject(@PathVariable Integer projectId, @RequestBody @Valid Project project) {
        projectService.updateProject(projectId, project);
        return ResponseEntity.status(200).body(new ApiResponse("Project Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project Deleted."));
    }

    // 3. Extra endpoints
    // 3.1 Search for DIY projects by difficulty level, time required in minutes, category, and budget
    @GetMapping("/searchByLevel/{difficultyLevel}/AndTime/{timeRequiredInMinutes}/AndCategory/{categoryId}/AndBudget/{budget}")
    public ResponseEntity searchByLevelAndTimeAndCategoryAndBudget(@PathVariable String difficultyLevel, @PathVariable String timeRequiredInMinutes, @PathVariable Integer categoryId , @PathVariable Double budget) {
        List<Project> projects = projectService.searchByLevelAndTimeAndCategoryAndBudget(difficultyLevel, timeRequiredInMinutes, categoryId, budget);
        return ResponseEntity.status(200).body(projects);
    }

    // 3.2 Start a DIY project
    @PutMapping("/startProject/{diyBeginnerId}/{projectId}")
    public ResponseEntity startProject(@PathVariable Integer diyBeginnerId, @PathVariable Integer projectId){
        projectService.startProject(diyBeginnerId,projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project Started."));
    }

    // 3.3 Complete a DIY project
    @PutMapping("/completeProject/{diyBeginnerId}/{projectId}")
    public ResponseEntity completeProject(@PathVariable Integer diyBeginnerId, @PathVariable Integer projectId){
        projectService.completeProject(diyBeginnerId,projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project Completed."));
    }

    // 3.4 Buy a DIY project
    @PutMapping("/buyProject/{diyBeginnerId}/{projectId}")
    public ResponseEntity buyProject(@PathVariable Integer diyBeginnerId, @PathVariable Integer projectId){
        projectService.buyProject(diyBeginnerId,projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Purchase Completed Successfully."));
    }

    // 3.4 Get trend projects
    @GetMapping("/getTrendProjects")
    public ResponseEntity getTrendProjects(){
        List<Project> trendProjects = projectService.getTrendProjects();
        return ResponseEntity.status(200).body(trendProjects);
    }

    // 3.5 Apply discount to a project
    @PutMapping("/applyDiscount/{projectId}/{diyExpertId}/{discountPercentage}")
    public ResponseEntity applyDiscount(@PathVariable Integer projectId, @PathVariable Integer diyExpertId ,@PathVariable Double discountPercentage){
        projectService.applyDiscount(projectId,diyExpertId,discountPercentage);
        return ResponseEntity.status(200).body(new ApiResponse("Discount Applied successfully."));
    }

    // 3.6 Allow DIY expert to add more project stock
    @PutMapping("/addStock/{diyExpertId}/{projectId}/{amount}")
    public ResponseEntity addStock(@PathVariable Integer diyExpertId, @PathVariable Integer projectId, @PathVariable Integer amount) {
        projectService.addStock(diyExpertId, projectId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("New Project Stock Added."));
    }
}