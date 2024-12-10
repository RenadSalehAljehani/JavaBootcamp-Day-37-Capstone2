package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // Using find
    Project findProjectByProjectId(Integer projectId);

    // Using JPQL
    @Query("select p from Project p where p.difficultyLevel = ?1 and p.timeRequiredInMinutes <= ?2")
    List<Project> getProjectsByDifficultyLevelAndTimeRequiredInMinutesAndCategoryId(String difficultyLevel, String timeRequiredInMinutes, Integer categoryId);
}