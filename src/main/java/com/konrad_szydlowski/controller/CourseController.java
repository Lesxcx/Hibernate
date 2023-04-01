package com.konrad_szydlowski.controller;

import com.konrad_szydlowski.exception.ResourceNotFoundException;
import com.konrad_szydlowski.model.Course;
import com.konrad_szydlowski.model.Instructor;
import com.konrad_szydlowski.repository.CourseRepository;
import com.konrad_szydlowski.repository.InstructorRepository;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("")
    public List<Course> getCourse() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return ResponseEntity.ok(course);
    }

    @PostMapping("")
    public Course createdCourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> addInstructorById(@Valid @PathVariable(value = "id") Long courseId,
                                                    @Valid @RequestBody Instructor instr) throws ResourceNotFoundException{

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Instructor instructor = instructorRepository.findById(instr.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        course.setInstructor(instructor);

        return ResponseEntity.ok(courseRepository.save(course));

    }

}
