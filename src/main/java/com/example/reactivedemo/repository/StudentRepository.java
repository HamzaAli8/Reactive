package com.example.reactivedemo.repository;

import com.example.reactivedemo.models.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {
}
