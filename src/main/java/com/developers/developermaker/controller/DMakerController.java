package com.developers.developermaker.controller;

import com.developers.developermaker.dto.CreateDeveloper;
import com.developers.developermaker.dto.DeveloperDetailDto;
import com.developers.developermaker.dto.DeveloperDto;
import com.developers.developermaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.valves.CrawlerSessionManagerValve;
import org.springframework.web.bind.annotation.*;

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
    public List<DeveloperDto> getAllDevelopers() {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers( // 응답을 CreateDeveloper.Response로 변경
            @Valid @RequestBody CreateDeveloper.Request request
            ) { // 요청값 받아오기
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }
}
