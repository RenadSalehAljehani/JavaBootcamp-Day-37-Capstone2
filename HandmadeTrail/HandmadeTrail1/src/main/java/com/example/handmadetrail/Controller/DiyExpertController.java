package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.DiyExpert;
import com.example.handmadetrail.Servise.DiyExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diyExpert")
@RequiredArgsConstructor
public class DiyExpertController {
    // 1. Declare a dependency for DiyExpertService using Dependency Injection
    private final DiyExpertService diyExpertService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllDiyExperts() {
        return ResponseEntity.status(200).body(diyExpertService.getAllDiyExperts());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addDiyExpert(@RequestBody @Valid DiyExpert diyExpert) {
        diyExpertService.addDiyExpert(diyExpert);
        return ResponseEntity.status(200).body(new ApiResponse("New Diy Expert Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{diyExpertId}")
    public ResponseEntity updateDiyExpert(@PathVariable Integer diyExpertId, @RequestBody @Valid DiyExpert diyExpert) {
        diyExpertService.updateDiyExpert(diyExpertId, diyExpert);
        return ResponseEntity.status(200).body(new ApiResponse("Diy Expert Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{diyExpertId}")
    public ResponseEntity deleteDiyBeginner(@PathVariable Integer diyExpertId) {
        diyExpertService.deleteDiyExpert(diyExpertId);
        return ResponseEntity.status(200).body(new ApiResponse("Diy Expert Deleted."));
    }
}
