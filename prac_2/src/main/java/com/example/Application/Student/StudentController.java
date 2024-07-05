package com.example.Application.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student") // Base path for all endpoints in this controller
public class StudentController {

    private final StudentRepository studentRepo;

    @Autowired
    public StudentController(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Students> getStudentById(@PathVariable("studentId") Long studentId) {
        Optional<Students> student = studentRepo.findById(studentId);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Students>> getAllStudents(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "1000") Integer size) {
      return ResponseEntity.ok(studentRepo.findAll());
    }


}
