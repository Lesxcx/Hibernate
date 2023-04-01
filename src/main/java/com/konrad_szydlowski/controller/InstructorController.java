package com.konrad_szydlowski.controller;

import com.konrad_szydlowski.exception.ResourceNotFoundException;
import com.konrad_szydlowski.model.Instructor;
import com.konrad_szydlowski.model.InstructorDetail;
import com.konrad_szydlowski.repository.InstructorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {


    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository instructorRepository)  {
        this.instructorRepository = instructorRepository;
    }
    @GetMapping("")
    public List<Instructor> getInstructors(){
        return instructorRepository.findAll();
    }

    @PostMapping("")
    public Instructor createInstructor(@Valid @RequestBody Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @PutMapping("/editDetails/{id}")
    public ResponseEntity<Instructor> updateInstructorDetail(@PathVariable(value = "id") Long instructorId,
                                                             @Valid @RequestBody InstructorDetail instructorDetail)
            throws ResourceNotFoundException {

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + instructorId));

        instructor.setDetail(instructorDetail);
        return ResponseEntity.ok(instructorRepository.save(instructor));
    }
}
