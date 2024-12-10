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
public class DiyBeginner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diyBeginnerId;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 5, max = 10, message = "Name length must be between 5-10 characters.")
    @Column(columnDefinition = "varchar(10) not null")
    @Check(constraints = "length(name) >= 5")
    private String name;

    @NotEmpty(message = "Username can't be empty.")
    @Size(min = 5, max = 10, message = "Username length must be between 5-10 characters.")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Check(constraints = "length(username) >= 5")
    private String username;

    @NotEmpty(message = "Password can't be empty.")
    @Size(min = 8, max = 20, message = "Password length must be between 8-20 characters.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    @Column(columnDefinition = "varchar(20) not null")
    @Check(constraints = "length(password) >= 8")
    private String password;

    @Email(message = "Invalid email format.")
    @NotEmpty(message = "Email can't be empty.")
    @Column(columnDefinition = "varchar(255) not null unique")
    @Check(constraints = "email LIKE '_%@_%._%'")
    private String email;

    @NotNull(message = "Balance can't be empty.")
    @Positive(message = "Balance must be a positive number larger than zero.")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "balance > 0")
    private Double balance;

    @Column(columnDefinition = "int default 0")
    private Integer points = 0;

    @JsonFormat(pattern = "yyyy-MM-dd' T 'HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime registrationDate;
}