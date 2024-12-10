package com.example.handmadetrail.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ProjectMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectMaterialId;

    @NotNull(message = "Project id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer projectId;

    @NotNull(message = "Material id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer materialId;

    @NotNull(message = "Quantity can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer quantity;
}