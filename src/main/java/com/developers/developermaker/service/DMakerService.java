package com.developers.developermaker.service;

import com.developers.developermaker.dto.CreateDeveloper;
import com.developers.developermaker.entity.Developer;
import com.developers.developermaker.exception.DMakerErrorCode;
import com.developers.developermaker.exception.DMakerException;
import com.developers.developermaker.repository.DeveloperRepository;
import com.developers.developermaker.type.DeveloperLevel;
import com.developers.developermaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.developers.developermaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validationCreateDeveloperRequest(request);

        Developer developer = Developer.builder() // Developer Entity를 builder를 통해 각각의 데이터 세팅
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .build();

        developerRepository.save(developer); // 빌드 완료한 디벨로퍼를 레퍼지토리에 세이브함으로써 영속화(저장)
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validationCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        if(developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

       developerRepository.findByMemberId(request.getMemberId())
               .ifPresent((developer -> {
                   throw new DMakerException(DUPLICATED_MEMBER_ID);
               }));
    }
}