package com.example.springcore.model;

import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.validator.ProductValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Product {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    // 관심 상품 생성 시 이용합니다.
    public Product(ProductRequestDto requestDto, Long userId) {
        // DI는 Bean 끼리만 적용이 가능합니다. Pruduct는 POJO이기 때문에 DI를 해줄 수 없습니다.
        // DI를 할 수 없지만 static으로 선언하면 객체가 없이도 함수를 사용할 수 있습니다.
        // Product 생성자의 엣지 케이스를 검정
        ProductValidator.validateProductConstructor(requestDto, userId);

        // 관심상품을 등록한 회원 Id 저장
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }
}
