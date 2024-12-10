package com.example.handmadetrail.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, max = 15, message = "Name length must be between 4-15 characters.")
    @Column(columnDefinition = "varchar(15) not null unique")
    @Check(constraints = "length(name) >= 4")
    private String name;
}