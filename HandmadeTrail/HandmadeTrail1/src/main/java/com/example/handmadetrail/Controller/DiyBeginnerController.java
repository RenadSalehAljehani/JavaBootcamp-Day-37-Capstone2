package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.DiyBeginner;
import com.example.handmadetrail.Servise.DiyBeginnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diyBeginner")
@RequiredArgsConstructor
public class DiyBeginnerController {
    // 1. Declare a dependency for DiyBeginnerService using Dependency Injection
    private final DiyBeginnerService diyBeginnerService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllDiyBeginners() {
        return ResponseEntity.status(200).body(diyBeginnerService.getAllDiyBeginners());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addDiyBeginner(@RequestBody @Valid DiyBeginner diyBeginner) {
        diyBeginnerService.addDiyBeginner(diyBeginner);
        return ResponseEntity.status(200).body(new ApiResponse("New Diy Beginner Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{diyBeginnerId}")
    public ResponseEntity updateDiyBeginner(@PathVariable Integer diyBeginnerId, @RequestBody @Valid DiyBeginner diyBeginner) {
        diyBeginnerService.updateDiyBeginner(diyBeginnerId, diyBeginner);
        return ResponseEntity.status(200).body(new ApiResponse("Diy Beginner Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{diyBeginnerId}")
    public ResponseEntity deleteDiyBeginner(@PathVariable Integer diyBeginnerId) {
        diyBeginnerService.deleteDiyBeginner(diyBeginnerId);
        return ResponseEntity.status(200).body(new ApiResponse("Diy Beginner Deleted."));
    }

    // 3. Extra endpoints
    // 3.1 Reward a DIY beginner
    @PutMapping("/reward/{diyBeginnerId}")
    public ResponseEntity reward(@PathVariable Integer diyBeginnerId){
        diyBeginnerService.reward(diyBeginnerId);
        return ResponseEntity.status(200).body(new ApiResponse("Diy Beginner Rewarded."));
    }

    // 3.2 Upgrade a DIY beginner
    @PutMapping("/upgrade/{diyBeginnerId}")
    public ResponseEntity upgrade(@PathVariable Integer diyBeginnerId){
        diyBeginnerService.upgrade(diyBeginnerId);
        return ResponseEntity.status(200).body(new ApiResponse("Diy Beginner Upgraded."));
    }
}