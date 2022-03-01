package com.example.reactivedemo.handlers;

import com.example.reactivedemo.models.Student;
import com.example.reactivedemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
}