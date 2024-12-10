package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.DiyExpert;
import com.example.handmadetrail.Repository.DiyExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiyExpertService {
    // 1. Declare a dependency for DiyExpertRepository using Dependency Injection
    private final DiyExpertRepository diyExpertRepository;

    // 2. CRUD
    // 2.1 Get
    public List<DiyExpert> getAllDiyExperts() {
        return diyExpertRepository.findAll();
    }

    // 2.2 Post
    public void addDiyExpert(DiyExpert diyExpert) {
        diyExpertRepository.save(diyExpert);
    }

    // 2.3 Update
    public void updateDiyExpert(Integer diyExpertId, DiyExpert diyExpert) {
        // 1. Check if diy-expert to be updated exists
        DiyExpert oldDiyExpert = diyExpertRepository.findDiyExpertByDiyExpertId(diyExpertId);
        if (oldDiyExpert == null) {
            throw new ApiException("Diy Expert Not Found.");
        }
        // 2. Set new values
        oldDiyExpert.setName(diyExpert.getName());
        oldDiyExpert.setUsername(diyExpert.getUsername());
        oldDiyExpert.setPassword(diyExpert.getPassword());
        oldDiyExpert.setEmail(diyExpert.getEmail());

        // 3. Save changes
        diyExpertRepository.save(oldDiyExpert);
    }

    // 2.4 Delete
    public void deleteDiyExpert(Integer diyExpertId) {
        // Check if diy-expert to be deleted exists
        DiyExpert oldDiyExpert = diyExpertRepository.findDiyExpertByDiyExpertId(diyExpertId);
        if (oldDiyExpert == null) {
            throw new ApiException("Diy Expert Not Found.");
        }
        diyExpertRepository.delete(oldDiyExpert);
    }
}