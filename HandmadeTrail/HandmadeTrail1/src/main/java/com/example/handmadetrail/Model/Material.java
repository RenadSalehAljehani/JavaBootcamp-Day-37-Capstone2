package com.example.handmadetrail.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId;

    @NotNull(message = "Material merchant id can't be null.")
    @Column(columnDefinition = "int not null")
    private Integer materialMerchantId;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 5, max = 20, message = "Name length must be between 5-20 characters.")
    @Column(columnDefinition = "varchar(20) not null unique")
    @Check(constraints = "length(name) >= 5")
    private String name;

    @NotNull(message = "Price can't be empty.")
    @Positive(message = "Price must be a positive number larger than zero.")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "price > 0")
    private Double price;

    @NotNull(message = "Stock can't be empty.")
    @Positive(message = "Stock must be a positive number larger than zero.")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "stock > 0")
    private Integer stock;
}