package com.example.handmadetrail.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @NotNull(message = "Beginner id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer diyBeginnerId;

    @NotNull(message = "Project id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer projectId;

    @NotNull(message = "Rating can't be empty.")
    @PositiveOrZero(message = "Rating must be a positive number or zero.")
    @Max(value = 5, message = "Rating must be between 0-5.")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "rating >= 0 and rating <= 5")
    private Integer rating;

    @NotEmpty(message = "Content can't be empty.")
    @Size(min = 4, max = 500, message = "Content length must be between 4-500 characters.")
    @Column(columnDefinition = "varchar(500) not null")
    @Check(constraints = "length(content) >= 4")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime commentDate;
}