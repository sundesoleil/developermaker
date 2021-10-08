package com.developers.developermaker.service;

import com.developers.developermaker.code.StatusCode;
import com.developers.developermaker.dto.CreateDeveloper;
import com.developers.developermaker.dto.DeveloperDetailDto;
import com.developers.developermaker.dto.DeveloperDto;
import com.developers.developermaker.dto.UpdateDeveloper;
import com.developers.developermaker.entity.Developer;
import com.developers.developermaker.entity.RetiredDeveloper;
import com.developers.developermaker.exception.DMakerException;
import com.developers.developermaker.repository.DeveloperRepository;
import com.developers.developermaker.repository.RetiredDeveloperRepository;
import com.developers.developermaker.type.DeveloperLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

import static com.developers.developermaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        return CreateDeveloper.Response.fromEntity(
                developerRepository.save(
                        createDeveloperFromRequest(request)
                )
        );
    }

    private Developer createDeveloperFromRequest(CreateDeveloper.Request request) {
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
    }


    private void validateCreateDeveloperRequest(@NonNull CreateDeveloper.Request request) {
        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );

        developerRepository.findByMemberId(request.getMemberId())
               .ifPresent((developer -> {
                   throw new DMakerException(DUPLICATED_MEMBER_ID);
               }));

    }

    @Transactional(readOnly = true)
    public List<DeveloperDto> getEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity) // Developer를 DeveloperDto로 바꾸는 매핑
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return DeveloperDetailDto.fromEntity(getDeveloperByMemberId(memberId));
    }

    private Developer getDeveloperByMemberId(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto updateDeveloper(
            String memberId, UpdateDeveloper.Request request
    ) {
        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );

        Developer developer = getDeveloperByMemberId(memberId);

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }


    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // 1. EMPLOYED -> RETIRED
        // 삭제할 Developer 정보를 가져오면서 간단한 exception 체크 함께 수행
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        // 디벨로퍼 상태 변경
        developer.setStatusCode(StatusCode.RETIRED);

        // 2. save into RetiredDeveloper4
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .build();
        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }

}