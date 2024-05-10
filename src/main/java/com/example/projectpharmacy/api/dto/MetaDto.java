package com.example.projectpharmacy.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {

    @JsonProperty("total_count") // jacksonㅇㅔ서 제공하는 어노테이션 json으로 응답 받을 때 현재 맵핑된 필드와 함께 매핑 시켜줌.
    private Integer totalCount;

}
