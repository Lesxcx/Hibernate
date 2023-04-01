package com.konrad_szydlowski.controller;

import com.konrad_szydlowski.exception.ResourceNotFoundException;
import com.konrad_szydlowski.model.User;
import com.konrad_szydlowski.service.CourseUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(name = "/courseUsers")
public class CourseUserController {

    private final CourseUserService courseUserService;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class RegisterForm{
        private long userId;
        private long courseId;
    }
    @PostMapping("")
    public Map<String, Boolean> register(@Valid @RequestBody RegisterForm registerForm) throws ResourceNotFoundException {
        boolean r = courseUserService.registerUserToCourse(registerForm.userId, registerForm.courseId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("registered: ", r);
        return response;
    }

    @GetMapping("{course_id}")
    public Set<User> getCourseUsers(@PathVariable(name = "course_id") Long id) throws ResourceNotFoundException {
        return courseUserService.getCourseUsers(id);
    }


}
