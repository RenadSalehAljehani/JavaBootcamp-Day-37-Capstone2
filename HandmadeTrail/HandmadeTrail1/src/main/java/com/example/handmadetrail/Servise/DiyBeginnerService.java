package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.DiyBeginner;
import com.example.handmadetrail.Model.DiyExpert;
import com.example.handmadetrail.Repository.CompletedProjectRepository;
import com.example.handmadetrail.Repository.DiyBeginnerRepository;
import com.example.handmadetrail.Repository.DiyExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiyBeginnerService {
    // 1. Declare a dependency for DiyBeginnerRepository using Dependency Injection
    private final DiyBeginnerRepository diyBeginnerRepository;

    // 2. Declare a dependency for DiyExpertRepository using Dependency Injection
    private final DiyExpertRepository diyExpertRepository;

    // 3. Declare a dependency for DiyExpertRepository using Dependency Injection
    private final CompletedProjectRepository completedProjectRepository;

    // 4. CRUD
    // 4.1 Get
    public List<DiyBeginner> getAllDiyBeginners() {
        return diyBeginnerRepository.findAll();
    }

    // 4.2 Post
    public void addDiyBeginner(DiyBeginner diyBeginner) {
        diyBeginnerRepository.save(diyBeginner);
    }

    // 3.3 Update
    public void updateDiyBeginner(Integer diyBeginnerId, DiyBeginner diyBeginner) {
        // 1. Check if diy-beginner to be updated exists
        DiyBeginner oldDiyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);
        if (oldDiyBeginner == null) {
            throw new ApiException("Diy Beginner Not Found.");
        }
        // 2. Set new values
        oldDiyBeginner.setName(diyBeginner.getName());
        oldDiyBeginner.setUsername(diyBeginner.getUsername());
        oldDiyBeginner.setPassword(diyBeginner.getPassword());
        oldDiyBeginner.setEmail(diyBeginner.getEmail());
        oldDiyBeginner.setBalance(diyBeginner.getBalance());
        oldDiyBeginner.setPoints(diyBeginner.getPoints());

        // 3. Save changes
        diyBeginnerRepository.save(oldDiyBeginner);
    }

    // 4.4 Delete
    public void deleteDiyBeginner(Integer diyBeginnerId) {
        // Check if diy-beginner to be deleted exists
        DiyBeginner oldDiyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);
        if (oldDiyBeginner == null) {
            throw new ApiException("Diy Beginner Not Found.");
        }
        diyBeginnerRepository.delete(oldDiyBeginner);
    }

    // 4. Extra endpoints
    // 4.1 Endpoint to rewards a DIY beginner by earning points
    public void reward(Integer diyBeginnerId) {
        // 1. Check if DIY beginner exists
        DiyBeginner diyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);

        if (diyBeginner == null) {
            throw new ApiException("Diy Beginner Not Found");
        }
        diyBeginner.setPoints(diyBeginner.getPoints() + 10); // Reward 10 points
        diyBeginnerRepository.save(diyBeginner);
    }

    // 4.2 Endpoint to upgrade a DIY beginner to an expert after reaching to 500 points
    public void upgrade(Integer diyBeginnerId) {
        // 1. Check if DIY beginner exists
        DiyBeginner diyBeginner = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(diyBeginnerId);

        if (diyBeginner == null) {
            throw new ApiException("Diy Beginner Not Found");
        }

        // 2. Check points
        if (diyBeginner.getPoints() >= 500) {
            // 3. Upgrade
            DiyExpert diyExpert = new DiyExpert();
            diyExpert.setName(diyBeginner.getName());
            diyExpert.setUsername(diyBeginner.getUsername());
            diyExpert.setPassword(diyBeginner.getPassword());
            diyExpert.setEmail(diyBeginner.getEmail());
            diyExpertRepository.save(diyExpert);
        }

        // 4. Delete the upgraded DIY beginner from beginner repository
        diyBeginnerRepository.delete(diyBeginner);
    }
}