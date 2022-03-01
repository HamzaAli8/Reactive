package com.example.reactivedemo;

import com.example.reactivedemo.repository.StudentRepository;
import com.example.reactivedemo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.studentRepository
                .deleteAll()
                .thenMany(studentService.saveAll("Evan", "Ji", "Harry", "Ryan", "Rick","Mash"))
                .thenMany(studentService.getAllStudents())
                .subscribe(s -> System.out.println(s.getName()));
    }


}