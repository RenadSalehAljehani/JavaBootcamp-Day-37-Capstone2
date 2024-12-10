package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.ProjectMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMaterialRepository extends JpaRepository<ProjectMaterial, InternalError> {

    // Using find
    ProjectMaterial findProjectMaterialByProjectMaterialId(Integer projectMaterialId);

    List<ProjectMaterial> findProjectMaterialsByProjectId(Integer projectId);

    // Using JPQL
    @Query("select count(pm) from ProjectMaterial pm where pm.projectId =?1")
    Integer countByProjectId(Integer projectId);
}