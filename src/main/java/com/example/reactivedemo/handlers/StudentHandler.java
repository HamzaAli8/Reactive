package com.example.reactivedemo.handlers;

import com.example.reactivedemo.models.Student;
import com.example.reactivedemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class StudentHandler {

    @Autowired
    StudentService studentService;

    public Mono<ServerResponse> getAll(ServerRequest req){

        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getAllStudents(), Student.class);

    }

    public Mono<ServerResponse> getStudent(ServerRequest req) {
        Integer studentId = Integer.parseInt(req.pathVariable("id"));
        return studentService.getStudent(studentId)
                .flatMap(student -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(student), Student.class))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());

    }

    public Mono<ServerResponse> addNewStudent(ServerRequest req){

        Mono<Student> studentMono = req.bodyToMono(Student.class);
        Mono<Student> newStudent = studentMono.flatMap(studentService::saveStudent);
        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newStudent, Student.class);
    }

    public Mono<ServerResponse> updateStudent(ServerRequest req){

        String studentId = req.pathVariable("id");
        Mono<Student> studentMono = req.bodyToMono(Student.class);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.updateStudent(Integer.valueOf(studentId), studentMono), Student.class);

    }

    public Mono<ServerResponse> deleteStudent(ServerRequest req) {
        String studentId = req.pathVariable("id");
        return studentService.getStudent(Integer.valueOf(studentId))
                .flatMap(student -> studentService
                        .deleteStudent(student)
                        .then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }





}