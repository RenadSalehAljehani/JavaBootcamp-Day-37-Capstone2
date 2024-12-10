package com.example.handmadetrail.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class CompletedProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer completedProjectId;

    @NotNull(message = "Beginner id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer diyBeginnerId;

    @NotNull(message = "Project id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer projectId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime completeDate;
}