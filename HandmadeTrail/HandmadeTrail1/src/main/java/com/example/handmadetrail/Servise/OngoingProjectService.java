package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.OngoingProject;
import com.example.handmadetrail.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OngoingProjectService {
    // 1. Declare a dependency for OngoingProjectRepository using Dependency Injection
    private final OngoingProjectRepository ongoingProjectRepository;

    // 2. CRUD
    // 2.1 Get
    public List<OngoingProject> getAllOngoingProjects(){
        return ongoingProjectRepository.findAll();
    }

    // 2.2 Delete
    public void deleteOngoingProject(Integer ongoingProjectId) {
        // Check if the ongoing project exists
        OngoingProject oldOngoingProject = ongoingProjectRepository.findOngoingProjectByOngoingProjectId(ongoingProjectId);

        if (oldOngoingProject == null) {
            throw new ApiException("Ongoing Project Not Found.");
        }
        ongoingProjectRepository.delete(oldOngoingProject);
    }
}