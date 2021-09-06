package com.developers.developermaker.dto;

import com.developers.developermaker.entity.Developer;
import com.developers.developermaker.type.DeveloperLevel;
import com.developers.developermaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    // Developer Entity로부터 DeveloperDto 생성
    public static DeveloperDto fromEntity(Developer developer) {
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}
