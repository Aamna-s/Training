package com.example.Application.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('admin', 'reporter')")
    @PostMapping("/api/v1/student/post")
    public ResponseEntity<Students> create(@RequestBody Students std){
        std=studentService.create(std);
        return ResponseEntity.created(URI.create("/api/v1/student/" + std.getStudentId())).body(std);
    }
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    @PutMapping("/api/v1/student/{studentId}")
    public ResponseEntity<Students> update(@PathVariable("studentId") Long studentId, @RequestBody Students std) {
        Optional<Students> saved = studentService.update(studentId, std);
        if (saved.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saved.get());
    }
    @DeleteMapping("/api/v1/student/{studentId}")
    public ResponseEntity<String> delete(@PathVariable("studentId") Long studentId) {
        if(studentService.delete(studentId))
        {
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
