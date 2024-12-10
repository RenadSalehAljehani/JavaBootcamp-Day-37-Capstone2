package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.OngoingProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngoingProjectRepository extends JpaRepository<OngoingProject, Integer> {

    boolean existsByDiyBeginnerIdAndProjectId(Integer diyBeginnerId, Integer projectId);

    // Using find
    OngoingProject findOngoingProjectByOngoingProjectId(Integer ongoingProjectId);

    OngoingProject findOngoingProjectByProjectId(Integer projectId);
}