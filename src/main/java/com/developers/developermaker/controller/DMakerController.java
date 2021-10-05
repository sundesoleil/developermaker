package com.developers.developermaker.controller;

import com.developers.developermaker.dto.*;
import com.developers.developermaker.exception.DMakerException;
import com.developers.developermaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

        return dMakerService.getEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable final String memberId
    ) {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers( // 응답을 CreateDeveloper.Response로 변경
            @Valid @RequestBody final CreateDeveloper.Request request
            ) { // 요청값 받아오기
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto updateDeveloper(
            @PathVariable final String memberId,
            @Valid @RequestBody final UpdateDeveloper.Request request
    ) {
        return dMakerService.updateDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper (
        @PathVariable final String memberId
        ) {
        return dMakerService.deleteDeveloper(memberId);
    }
}
