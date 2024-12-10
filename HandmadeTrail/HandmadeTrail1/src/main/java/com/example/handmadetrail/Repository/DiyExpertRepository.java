package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.DiyExpert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiyExpertRepository extends JpaRepository<DiyExpert, Integer> {
    // Using find
    DiyExpert findDiyExpertByDiyExpertId(Integer diyExpertId);
}