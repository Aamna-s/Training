package com.example.Application.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Students {
    @Id
    private  Long studentId;
    private String name;
    private String grade;


}
