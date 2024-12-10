package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.CompletedProject;
import com.example.handmadetrail.Model.DiyBeginner;
import com.example.handmadetrail.Repository.CompletedProjectRepository;
import com.example.handmadetrail.Repository.DiyBeginnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedProjectService {
    // 1. Declare a dependency for CompletedProjectRepository using Dependency Injection
    private final CompletedProjectRepository completedProjectRepository;

    // 2. Declare a dependency for DiyBeginnerRepository using Dependency Injection
    private final DiyBeginnerRepository diyBeginnerRepository;

    // 3. CRUD
    // 3.1 Get
    public List<CompletedProject> getAllCompletedProjects() {
        return completedProjectRepository.findAll();
    }

    // 3.2 Delete
    public void deleteCompletedProject(Integer completedProjectId) {
        // Check if the completed project exists
        CompletedProject oldCompletedProject = completedProjectRepository.findOngoingProjectByCompletedProjectId(completedProjectId);

        if (oldCompletedProject == null) {
            throw new ApiException("Completed Project Not Found.");
        }
        completedProjectRepository.delete(oldCompletedProject);
    }

    // 4. Extra endpoint
    // An endpoint to get all completed projects for a DIY beginner by id
    public List<CompletedProject> getAllCompletedProjectsForABeginner(Integer diyBeginnerId) {
        // Check if the DIY beginner exists
        DiyBeginner diyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);

        if (diyBeginner == null) {
            throw new ApiException("Diy Beginner Not Found.");
        }
       List<CompletedProject> completedProjects = completedProjectRepository.findCompletedProjectsByDiyBeginnerId(diyBeginnerId);

        if (completedProjects.isEmpty()) {
            throw new ApiException("There Are No Completed Project For Diy Beginner With Id (" + diyBeginner + ").");
        }
        return completedProjects;
    }
}