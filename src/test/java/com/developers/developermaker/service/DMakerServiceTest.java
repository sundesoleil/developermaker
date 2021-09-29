package com.developers.developermaker.service;

import com.developers.developermaker.code.StatusCode;
import com.developers.developermaker.dto.DeveloperDetailDto;
import com.developers.developermaker.entity.Developer;
import com.developers.developermaker.repository.DeveloperRepository;
import com.developers.developermaker.repository.RetiredDeveloperRepository;

import com.developers.developermaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.developers.developermaker.type.DeveloperLevel.SENIOR;
import static com.developers.developermaker.type.DeveloperSkillType.BACK_END;
import static com.developers.developermaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    @Test
    public void createDeveloperTest() {

        // Mock들의 동작 정의
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                                .developerLevel(SENIOR)
                                .developerSkillType(DeveloperSkillType.BACK_END)
                                .experienceYears(12)
                                .statusCode(StatusCode.EMPLOYED)
                                .name("name")
                                .age(45)
                                .build()));

        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(BACK_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

}