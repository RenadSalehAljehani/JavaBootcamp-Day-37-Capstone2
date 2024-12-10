package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.*;
import com.example.handmadetrail.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    // 1. Declare a dependency for ProjectRepository using Dependency Injection
    private final ProjectRepository projectRepository;

    // 2. Declare a dependency for CategoryRepository using Dependency Injection
    private final CategoryRepository categoryRepository;

    // 3. Declare a dependency for DiyExpertRepository using Dependency Injection
    private final DiyExpertRepository diyExpertRepository;

    // 4. Declare a dependency for DiyExpertRepository using Dependency Injection
    private final DiyBeginnerRepository diyBeginnerRepository;

    // 5. Declare a dependency for ProjectMaterialRepository using Dependency Injection
    private final ProjectMaterialRepository projectMaterialRepository;

    // 6. Declare a dependency for MaterialRepository using Dependency Injection
    private final MaterialRepository MaterialRepository;

    // 7. Declare a dependency for OngoingProjectRepository using Dependency Injection
    private final OngoingProjectRepository ongoingProjectRepository;

    // 8. Declare a dependency for CompletedProjectRepository using Dependency Injection
    private final CompletedProjectRepository completedProjectRepository;

    // 9. Declare a dependency for DiyBeginnerService using Dependency Injection
    private final DiyBeginnerService diyBeginnerService;

    // 10. Declare a dependency for CommentRepository using Dependency Injection
    private final CommentRepository commentRepository;

    // 11. CRUD
    // 11.1 Get
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // 11.2 Post
    public void addProject(Project project) {
        // Check the existence of category id and diy-expert id for the project
        boolean invalidCategory = categoryRepository.findCategoryByCategoryId(project.getCategoryId()) == null;
        boolean invalidDiyExpertId = diyExpertRepository.findDiyExpertByDiyExpertId(project.getDiyExpertId()) == null;

        if (invalidCategory && invalidDiyExpertId) {
            throw new ApiException("Category and Diy Expert Not Found.");
        }
        if (invalidCategory) {
            throw new ApiException("Category Not Found.");
        }
        if (invalidDiyExpertId) {
            throw new ApiException("Diy Expert Not Found.");
        }
        projectRepository.save(project);
    }

    // 11.3 Update
    public void updateProject(Integer projectId, Project project) {
        // 1. Check if project to be updated exists
        Project oldProject = projectRepository.findProjectByProjectId(projectId);
        if (oldProject == null) {
            throw new ApiException("Project Not Found.");
        }

        // 2. Check the existence of category id and diy-expert id for the project
        boolean invalidCategory = categoryRepository.findCategoryByCategoryId(project.getCategoryId()) == null;
        boolean invalidDiyExpertId = diyExpertRepository.findDiyExpertByDiyExpertId(project.getDiyExpertId()) == null;

        if (invalidCategory && invalidDiyExpertId) {
            throw new ApiException("Category and Diy Expert Not Found.");
        }
        if (invalidCategory) {
            throw new ApiException("Category Not Found.");
        }
        if (invalidDiyExpertId) {
            throw new ApiException("Diy Expert Not Found.");
        }

        // 3. Set new values
        oldProject.setTitle(project.getTitle());
        oldProject.setDescription(project.getDescription());
        oldProject.setDifficultyLevel(project.getDifficultyLevel());
        oldProject.setTimeRequiredInMinutes(project.getTimeRequiredInMinutes());
        oldProject.setNumberOfMaterials(project.getNumberOfMaterials());
        oldProject.setSteps(project.getSteps());
        oldProject.setCategoryId(project.getCategoryId());
        oldProject.setDiyExpertId(project.getDiyExpertId());
        oldProject.setStock(project.getStock());

        // 4. Save changes
        projectRepository.save(oldProject);
    }

    // 11.4 Delete
    public void deleteProject(Integer projectId) {
        // Check if project to be deleted exists
        Project oldProject = projectRepository.findProjectByProjectId(projectId);
        if (oldProject == null) {
            throw new ApiException("Project Not Found.");
        }
        projectRepository.delete(oldProject);
    }

    // 12. Extra endpoints
    // 12.1 Endpoint to estimate a DIY project budget
    public void budgetEstimator(Integer projectId) {
        // 1. Check if the project exists
        Project project = projectRepository.findProjectByProjectId(projectId);
        if (project == null) {
            throw new ApiException("Project Not Found.");
        }

        // 2. Reset the budget to 0
        project.setBudget(0.0);

        // 3. Loop through project materials
        List<ProjectMaterial> projectMaterials = projectMaterialRepository.findProjectMaterialsByProjectId(projectId);
        for (ProjectMaterial projectMaterial : projectMaterials) {
            // 4. Retrieve the price for each material in the project
            Material material = MaterialRepository.findMaterialByMaterialId(projectMaterial.getMaterialId());
            if (material == null) {
                throw new ApiException("Material Not Found for Material ID: " + projectMaterial.getMaterialId());
            }
            // 5. Calculate the total cost for the material
            double materialCost = material.getPrice() * projectMaterial.getQuantity();
            project.setBudget(project.getBudget() + materialCost);
        }

        // 6. Save the changes
        projectRepository.save(project);
    }

    // 12.2 Endpoint to estimate a DIY project price based on its estimated budget
    public void priceEstimator(Integer projectId) {
        // 1. Use the budgetEstimator method to calculate the project's budget
        budgetEstimator(projectId);

        // 2. Retrieve the updated project
        Project project = projectRepository.findProjectByProjectId(projectId);
        if (project == null) {
            throw new ApiException("Project Not Found.");
        }

        // 3. Retrieve the calculated budget
        double projectBudget = project.getBudget();

        // 4. Determine the profit margin based on project required time (in minutes) and difficulty level
        double requiredTimeInMinutes = project.getTimeRequiredInMinutes();
        String difficultyLevel = project.getDifficultyLevel();

        // 5. Base profit margin
        double profitMarginPercentage = 10.0; // Default for "Easy"

        // 6. Adjust profit margin based on difficulty level
        if ("Intermediate".equalsIgnoreCase(difficultyLevel)) {
            profitMarginPercentage += 5.0; // Add 5% for medium difficulty
        } else if ("Hard".equalsIgnoreCase(difficultyLevel)) {
            profitMarginPercentage += 10.0; // Add 10% for hard difficulty
        }

        // 7. Further adjust profit margin based on required time
        if (requiredTimeInMinutes > 600) { // Projects longer than 10 hours
            profitMarginPercentage += 5.0; // Add 5% for extended time
        }

        // 8. Calculate the selling price
        double sellingPrice = projectBudget + (projectBudget * profitMarginPercentage / 100);

        // 9. Update the project with the selling price
        project.setPrice(sellingPrice);
        projectRepository.save(project);
    }

    // 12.3 Endpoint to search for DIY projects by difficulty level, time required in minutes, category, and budget
    public List<Project> searchByLevelAndTimeAndCategoryAndBudget(String difficultyLevel, String timeRequiredInMinutes, Integer categoryId, Double budget) {
        // 1. Check difficulty level
        if (!difficultyLevel.equalsIgnoreCase("easy")
                && !difficultyLevel.equalsIgnoreCase("intermediate")
                && !difficultyLevel.equalsIgnoreCase("hard")) {
            throw new ApiException("Difficulty Level Must Difficulty Easy, Intermediate, or Hard.");
        }
        // 2. Check the existence of category id
        boolean invalidCategoryId = categoryRepository.findCategoryByCategoryId(categoryId) == null;

        if (invalidCategoryId) {
            throw new ApiException("Category Not Found.");
        }

        // 3. Retrieve projects matching the difficulty level and time criteria
        List<Project> projects = projectRepository.getProjectsByDifficultyLevelAndTimeRequiredInMinutesAndCategoryId(difficultyLevel, timeRequiredInMinutes, categoryId);

        // 4. Filter DIY projects further based on their estimated budget
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getBudget() <= budget) { // Check if the budget is within the specified limit
                filteredProjects.add(project);
            }
        }
        // 5. Throw an exception if no projects match all criteria
        if (filteredProjects.isEmpty()) {
            throw new ApiException("No Projects Within Specified Criteria Has Been Found.");
        }
        return filteredProjects;
    }

    // 12.4 Endpoint to allow DIY beginners to start implementing a DIY project
    public void startProject(Integer diyBeginnerId, Integer projectId) {
        // 1. Check the existence of diy-beginner id and project id
        Boolean invalidDiyBeginnerId = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId) == null;
        Boolean invalidProjectId = projectRepository.findProjectByProjectId(projectId) == null;

        if (invalidDiyBeginnerId && invalidProjectId) {
            throw new ApiException("Diy Beginner Id and Project Id Not Found.");
        }
        if (invalidDiyBeginnerId) {
            throw new ApiException("Diy Beginner Id Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Id Not Found.");
        }

        // 2. Check if the project is already ongoing
        boolean alreadyStarted = ongoingProjectRepository.existsByDiyBeginnerIdAndProjectId(diyBeginnerId, projectId);

        if (alreadyStarted) {
            throw new ApiException("Project is Already Ongoing.");
        }

        OngoingProject ongoingProject = new OngoingProject();
        ongoingProject.setDiyBeginnerId(diyBeginnerId);
        ongoingProject.setProjectId(projectId);
        ongoingProjectRepository.save(ongoingProject);
    }

    // 12.5 Endpoint to allow DIY beginners to complete implementing a DIY project
    public void completeProject(Integer diyBeginnerId, Integer projectId) {
        // 1. Check the existence of diy-beginner id and project id
        Boolean invalidDiyBeginnerId = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId) == null;
        Boolean invalidProjectId = projectRepository.findProjectByProjectId(projectId) == null;

        if (invalidDiyBeginnerId && invalidProjectId) {
            throw new ApiException("Diy Beginner Id and Project Id Not Found.");
        }
        if (invalidDiyBeginnerId) {
            throw new ApiException("Diy Beginner Id Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Id Not Found.");
        }

        // 2. Check if the project is ongoing
        boolean isStarted = ongoingProjectRepository.existsByDiyBeginnerIdAndProjectId(diyBeginnerId, projectId);

        if (!isStarted) {
            throw new ApiException("Project Not Started Yet.");
        }

        // 3. Retrieve the ongoing project and check completion time
        OngoingProject ongoingProject = ongoingProjectRepository.findOngoingProjectByProjectId(projectId);

        // 4. Save the completed project
        CompletedProject completedProject = new CompletedProject();
        completedProject.setDiyBeginnerId(diyBeginnerId);
        completedProject.setProjectId(projectId);
        completedProject.setCompleteDate(LocalDateTime.now());
        completedProjectRepository.save(completedProject);

        // 5. Delete the project from ongoing projects
        ongoingProjectRepository.delete(ongoingProject);

        LocalDateTime startDate = ongoingProject.getStartDate();
        LocalDateTime completedDate = completedProject.getCompleteDate();

        // 6. Check if the project completed within 24 hours
        boolean isCompletedWithin24Hours = startDate.plusHours(24).isAfter(completedDate);

        if (isCompletedWithin24Hours) {
            // 7. Reward DIY beginner when completing DIY projects within 24 hours
            diyBeginnerService.reward(diyBeginnerId);
        }
    }

    //  Endpoint to allow a DIY beginner to buy a DIY project
    public void buyProject(Integer diyBeginnerId, Integer projectId) {
        // 1. Check the existence of diy-beginner id and project id
        Boolean invalidDiyBeginnerId = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId) == null;
        Boolean invalidProjectId = projectRepository.findProjectByProjectId(projectId) == null;

        if (invalidDiyBeginnerId && invalidProjectId) {
            throw new ApiException("Diy Beginner Id and Project Id Not Found.");
        }
        if (invalidDiyBeginnerId) {
            throw new ApiException("Diy Beginner Id Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Id Not Found.");
        }

        // 2. Find project by id
        Project project = projectRepository.findProjectByProjectId(projectId);

        // 3. Get DIY beginner balance
        Double balance = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId).getBalance();

        // 4. Check if balance is enough
        if (project.getPrice() <= balance) {
            // 5. Check stock
            if (project.getStock() != 0) {
                // 6. Reduce project stock by one
                project.setStock(project.getStock() - 1);
                // 7. Save changes
                projectRepository.save(project);
            } else {
                throw new ApiException("Project Out Of Stock.");
            }
        } else {
            throw new ApiException("Insufficient Balance.");
        }
    }

    // 12.6 Endpoint to get trend projects with high rating ( 4 out of 5 or above)
    public List<Project> getTrendProjects() {
        List<Comment> highRatings = commentRepository.getHighRatings();
        List<Project> trendProjects = new ArrayList<>();
        for (Project project : projectRepository.findAll()) {
            for (Comment comment : highRatings) {
                if (project.getProjectId().equals(comment.getProjectId()))
                    trendProjects.add(project);
            }
        }
        if (trendProjects.isEmpty()) {
            throw new ApiException("No Trend Projects Has Been Found.");
        }
        return trendProjects;
    }

    // 12.7 Endpoint to allow DIY experts to apply discount on project for sale
    public void applyDiscount(Integer projectId, Integer diyExpertId, Double discountPercentage) {
        // 1. Validate if the project exists
        Project project = projectRepository.findProjectByProjectId(projectId);
        if (project == null) {
            throw new ApiException("Project Not Found.");
        }

        // 2. Ensure the selling price is set
        double originalPrice = project.getPrice();
        if (originalPrice <= 0) {
            throw new ApiException("Selling Price Not Set. Unable to apply discount.");
        }

        // 3. Validate the discount percentage
        if (discountPercentage < 0 || discountPercentage > 50) { // Maximum discount: 50%
            throw new ApiException("Invalid Discount Percentage. It must be between 0 and 50.");
        }

        // 4. Authorization check (only experts can apply discounts)
        if (diyExpertRepository.findDiyExpertByDiyExpertId(diyExpertId) == null) {
            throw new ApiException("Unauthorized Action. Only experts can apply discounts.");
        }

        // 5. Calculate the discounted price
        double discountAmount = (originalPrice * discountPercentage) / 100;
        double discountedPrice = originalPrice - discountAmount;

        // 6. Apply the discount and expiration date to the project
        project.setPrice(discountedPrice);

        // 7. Save the updated project
        projectRepository.save(project);
    }

    // 12.8 Endpoint to allow DIY expert to add more project stock
    public void addStock(Integer diyExpertId, Integer projectId, Integer amount) {
        // 1. Check if amount is valid
        if (amount <= 0) {
            throw new ApiException("Project Amount Should Be a Positive Number Larger Than Zero.");
        }

        // 2. Check the existence of DIY expert id and project id
        boolean invalidDiyExpertId = diyExpertRepository.findDiyExpertByDiyExpertId(diyExpertId) == null;
        boolean invalidProjectId = projectRepository.findProjectByProjectId(projectId) == null;

        if (invalidDiyExpertId && invalidProjectId) {
            throw new ApiException("DIY Expert and Project Not Found.");
        }
        if (invalidDiyExpertId) {
            throw new ApiException("DIY Expert Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Not Found.");
        }

        // 3. Add stock
        Project project = projectRepository.findProjectByProjectId(projectId);
        project.setStock(project.getStock() + amount);

        // 4. Save the changes
        projectRepository.save(project);
    }
}