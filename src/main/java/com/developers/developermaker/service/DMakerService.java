package com.developers.developermaker.service;

import com.developers.developermaker.dto.CreateDeveloper;
import com.developers.developermaker.entity.Developer;
import com.developers.developermaker.repository.DeveloperRepository;
import com.developers.developermaker.type.DeveloperLevel;
import com.developers.developermaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper(CreateDeveloper.@Valid Request request) {
        Developer developer = Developer.builder() // Developer Entity를 builder를 통해 각각의 데이터 세팅
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .experienceYears(2)
                .name("Olaf")
                .age(35)
                .build();

        developerRepository.save(developer); // 빌드 완료한 디벨로퍼를 레퍼지토리에 세이브함으로써 영속화(저장)
        }
    }