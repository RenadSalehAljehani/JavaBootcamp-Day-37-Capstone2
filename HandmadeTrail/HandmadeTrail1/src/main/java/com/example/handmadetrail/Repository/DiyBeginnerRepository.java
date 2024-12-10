package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.DiyBeginner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiyBeginnerRepository extends JpaRepository<DiyBeginner, Integer> {
    // Using find
    DiyBeginner findDiyBeginnerByDiyBeginnerId(Integer diyBeginnerId);
}