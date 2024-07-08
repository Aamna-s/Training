package com.example.Application.Student;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepo;
    public  StudentService(StudentRepository studentRepo){this.studentRepo=studentRepo;}
    public Optional<Students> findById(Long newsId) {
        return studentRepo.findById(newsId);
    }
    public List<Students> findAll(Integer page, Integer size)
    {
        if (page<0) { page=0; }
        if (size>1000){size=1000;}
        return studentRepo.findAll(PageRequest.of(page,size)).getContent();
    }
    public Students create(Students std)
    {
        return studentRepo.save(std);

    }
}
