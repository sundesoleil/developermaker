package com.developers.developermaker.controller;

import com.developers.developermaker.dto.CreateDeveloper;
import com.developers.developermaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return Arrays.asList("snow", "Elsa", "Olaf");
    }

    @GetMapping("/create-developer")
    public List<String> createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            ) {
        log.info("request : {}", request);

        dMakerService.createDeveloper(request);

        return Collections.singletonList("Olaf"); // 단일 객체 리스트
    }
}
