package com.example.Application.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
 // Base path for all endpoints in this controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/api/v1/student/{studentId}")
    public ResponseEntity<Students> getStudentById(@PathVariable("studentId") Long studentId) {
        Optional<Students> student = studentService.findById(studentId);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/v1/student")
    public ResponseEntity<List<Students>> getAllStudents(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "1000") Integer size) {
      return ResponseEntity.ok(studentService.findAll(page,size));
    }

    @PostMapping("/api/v1/student/post")
    public ResponseEntity<Students> create(@RequestBody Students std){
        std=studentService.create(std);
        return ResponseEntity.created(URI.create("/api/v1/student/" + std.getStudentId())).body(std);
    }
}
