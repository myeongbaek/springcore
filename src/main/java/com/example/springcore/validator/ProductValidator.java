package com.example.springcore.validator;

import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.error.InvalidUserIdValidException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    public static void validateProductConstructor(ProductRequestDto requestDto, Long userId) {
        // 입력값 Validation
        if (userId == null || userId <= 0) {
            throw new InvalidUserIdValidException("회원 Id 가 유효하지 않습니다.");
        }

        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("저장할 수 있는 상품명이 없습니다.");
        }

        if (!UrlValidator.isValidUrl(requestDto.getImage())) {
            throw new IllegalArgumentException("상품 이미지 URL 포맷이 맞지 않습니다.");
        }

        if (!UrlValidator.isValidUrl(requestDto.getLink())) {
            throw new IllegalArgumentException("상품 최저가 페이지 URL 포맷이 맞지 않습니다.");
        }

        if (requestDto.getLprice() <= 0) {
            throw new IllegalArgumentException("상품 최저가가 0 이하입니다.");
        }
    }
}
