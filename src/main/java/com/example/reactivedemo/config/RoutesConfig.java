package com.example.reactivedemo.config;

import com.example.reactivedemo.models.Student;
import com.example.reactivedemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RoutesConfig {

    @Autowired
    StudentService studentService;

    @Bean
    public RouterFunction<ServerResponse> router(){

        return route().GET("/students-route", request ->
                ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(studentService.getAllStudents(), Student.class)).build();
    }


}
