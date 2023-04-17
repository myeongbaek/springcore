package com.example.springcore.model;

import com.example.springcore.dto.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTest {
    @Test
    @DisplayName("정상 케이스")
        // 테스트를 진행할 함수이름을 대신합니다.
    void createProduct_Normal() {
        // given : 이러이러한 환경에서서
        // 생성자 함수를 테스트 하기위해 샘플 값들을 나열합니다.
        Long userId = 100L;
        String title = "오리온 꼬북칩 초코츄러스맛 160g";
        String image = "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";
        String link = "https://search.shopping.naver.com/gate.nhn?id=24161228524";
        int lprice = 2350;

        ProductRequestDto requestDto = new ProductRequestDto(title, image, link, lprice);

        // when : 다음과 같이 실행될 때
        // Product를 테스트하기 위한 조건 입니다. 생성자 함수를 호출합니다.
        Product product = new Product(requestDto, userId);

        // then : 이러이러한 조건들을 충족해야 합니다.
        // product를 검증합니다.
        // assert는 사실임을 강하게 주장합니다.
        assertNull(product.getId()); // null이어야 에러를 발생하지 않고 정상적으로 봅니다.
        assertEquals(userId, product.getUserId()); // 같아야 에러를 발생하지 않고 정상적으로 봅니다.
        assertEquals(title, product.getTitle()); // expected와 actual을 비교합니다.
        assertEquals(image, product.getImage());
        assertEquals(link, product.getLink());
        assertEquals(lprice, product.getLprice());
        assertEquals(0, product.getMyprice()); // 정책상 바뀐 결정은 테스트 코드에도 반영이 되어야 하기 때문에 테스트 코드는 높은 비용을 요구합니다.
    }
}
