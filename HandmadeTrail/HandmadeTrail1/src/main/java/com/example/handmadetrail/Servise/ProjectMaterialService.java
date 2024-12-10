package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.Project;
import com.example.handmadetrail.Model.ProjectMaterial;
import com.example.handmadetrail.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMaterialService {
    // 1. Declare a dependency for ProjectMaterialRepository using Dependency Injection
    private final ProjectMaterialRepository projectMaterialRepository;

    // 2. Declare a dependency for ProjectRepository using Dependency Injection
    private final ProjectRepository projectRepository;

    // 3. Declare a dependency for MaterialRepository using Dependency Injection
    private final MaterialRepository materialRepository;
    private final ProjectService projectService;

    // 2. CRUD
    // 2.1 Get
    public List<ProjectMaterial> getAllProjectMaterials() {
        return projectMaterialRepository.findAll();
    }

    // 2.2 Post
    public void addProjectMaterial(ProjectMaterial projectMaterial) {
        // 1. Check the existence of project id and material id
        Boolean invalidProjectId = projectRepository.findProjectByProjectId(projectMaterial.getProjectId()) == null;
        Boolean invalidMaterialId = materialRepository.findMaterialByMaterialId(projectMaterial.getMaterialId()) == null;

        if (invalidProjectId && invalidMaterialId) {
            throw new ApiException("Project and Material Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Not Found.");
        }
        if (invalidMaterialId) {
            throw new ApiException("Material Not Found.");
        }

        // 2. Get number of materials specified for the project
        Project project = projectRepository.findProjectByProjectId(projectMaterial.getProjectId());
        Integer numberOfMaterials = project.getNumberOfMaterials();

        // 3. Get the current count of materials added for the project
        Integer currentMaterialCount = projectMaterialRepository.countByProjectId(projectMaterial.getProjectId());

        // 4. Check that added materials does not exceed the limit
        if (currentMaterialCount >= numberOfMaterials) {
            throw new ApiException("You Exceeded Number of Materials Specified For the Project.");
        }

        // 5. Save the project material
        projectMaterialRepository.save(projectMaterial);

        // 6. Estimate project budget
        projectService.budgetEstimator(project.getProjectId());

        // 7. Estimate project selling price
        projectService.priceEstimator(project.getProjectId());
    }

    // 2.3 Update
    public void updateProjectMaterial(Integer projectMaterialId, ProjectMaterial projectMaterial) {
        // 1. Check if project material to be updated exists
        ProjectMaterial oldProjectMaterial = projectMaterialRepository.findProjectMaterialByProjectMaterialId(projectMaterialId);
        if (oldProjectMaterial == null) {
            throw new ApiException("Project Material Not Found.");
        }
        // 2. Check the existence of project id and material id
        Boolean invalidProjectId = projectRepository.findProjectByProjectId(projectMaterial.getProjectId()) == null;
        Boolean invalidMaterialId = materialRepository.findMaterialByMaterialId(projectMaterial.getMaterialId()) == null;

        if (invalidProjectId && invalidMaterialId) {
            throw new ApiException("Project and Material Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Not Found.");
        }
        if (invalidMaterialId) {
            throw new ApiException("Material Not Found.");
        }
        // 3. Set new values
        oldProjectMaterial.setMaterialId(projectMaterial.getMaterialId());
        oldProjectMaterial.setProjectId(projectMaterial.getProjectId());
        oldProjectMaterial.setQuantity(projectMaterial.getQuantity());

        // 4. Save changes
        projectMaterialRepository.save(oldProjectMaterial);
    }

    // 2.4 Delete
    public void deleteProjectMaterial(Integer projectMaterialId) {
        // 1. Check if project material to be deleted exists
        ProjectMaterial oldProjectMaterial = projectMaterialRepository.findProjectMaterialByProjectMaterialId(projectMaterialId);
        if (oldProjectMaterial == null) {
            throw new ApiException("Project Material Not Found.");
        }
        projectMaterialRepository.delete(oldProjectMaterial);
    }
}
