package com.developers.developermaker.dto;

import com.developers.developermaker.entity.Developer;
import com.developers.developermaker.type.DeveloperLevel;
import com.developers.developermaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloper {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId size must be between 3 and 50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 20, message = "memberId size must be between 3 and 20")
        private String name;
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response { // 개발자 생성에 대한 응답
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String memberId;

        // responseDto를 만들때는 주로 우리가 Developer를 생성한 직후에 바로 DeveloperEntity를 통해 만들어주는
        // 거라서 developerEntity와 상당히 강한 결합을 하게 된다. 이럴 때는 fromEntity를 사용해서
        // developerEntity를 받아서 바로 response를 만들어주는 static method를 생성한다.
       public static Response fromEntity(Developer developer) {
            return Response.builder()
                    .developerLevel(developer.getDeveloperLevel())
                    .developerSkillType(developer.getDeveloperSkillType())
                    .experienceYears(developer.getExperienceYears())
                    .memberId(developer.getMemberId())
                    .build();
        }
    }
}
