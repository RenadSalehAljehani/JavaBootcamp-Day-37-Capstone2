package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.CompletedProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompletedProjectRepository extends JpaRepository<CompletedProject, Integer> {
    // Using find
    CompletedProject findOngoingProjectByCompletedProjectId(Integer completedProjectId);

    List<CompletedProject> findCompletedProjectsByDiyBeginnerId(Integer diyBeginnerId);
}