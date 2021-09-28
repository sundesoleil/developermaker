package com.developers.developermaker.service;

import com.developers.developermaker.dto.CreateDeveloper;
import com.developers.developermaker.dto.DeveloperDto;
import com.developers.developermaker.type.DeveloperLevel;
import com.developers.developermaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DMakerServiceTest {

    @Autowired
    private DMakerService dMakerService;

    @Test
    public void createDeveloperTest() {
        dMakerService.createDeveloper(CreateDeveloper.Request.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .experienceYears(12)
                .memberId("memberId")
                .name("name")
                .age(40)
                .build()
        );
        List<DeveloperDto> employedDevelopers = dMakerService.getEmployedDevelopers();
        System.out.println("=================");
        System.out.println(employedDevelopers);
        System.out.println("=================");
    }
}