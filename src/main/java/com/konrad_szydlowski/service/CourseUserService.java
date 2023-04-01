package com.konrad_szydlowski.service;

import com.konrad_szydlowski.exception.ResourceNotFoundException;
import com.konrad_szydlowski.model.Course;
import com.konrad_szydlowski.model.User;
import com.konrad_szydlowski.repository.CourseRepository;
import com.konrad_szydlowski.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class CourseUserService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public boolean registerUserToCourse(Long userId, Long courseId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Not found user"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found courses"));

        user.getCourses().add(course);
        course.getUsers().add(user);
        userRepository.save(user);
        courseRepository.save(course);

//        Map<String, Boolean> status = new HashMap<>();
//        status.put("added", Boolean.TRUE);
        return true;
    }

    public Set<User> getCourseUsers(long courseId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found course"));

        return course.getUsers();
    }
}
