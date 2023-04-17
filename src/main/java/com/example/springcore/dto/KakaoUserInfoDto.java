package com.example.springcore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 생성자 자동생성
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
}
