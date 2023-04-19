package com.example.springcore.integration;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserProductIntegrationTest {
    // 관심 상품 등록, 업데이트, 조회 기능을 테스트 합니다.

    // 추가되는 테스트 목록 :
    // 1. 회원가입 하지 않은 유저의 관심 상품 등록을 막습니다.
    // 2. 회원가입 기능 테스트
    // 3. 가입된 회원의 관심 상품 등록 테스트
    // 4. 관심상품 업데이트 테스트
    // 5. 조회 테스트

    // @SpringBootTest는 bean을 사용 가능케 해줍니다.
    // DI 해주고 있습니다.다.
//    @Autowired
//    ProductService productService;


    @Nested
    @DisplayName("유저 테스트")
    class UserTest {
        // 1. 회원가입 하지 않은 유저의 관심 상품 등록을 막습니다.
        @Test
        @Order(1)
        @DisplayName("미가입된 사용자의 관심상품 등록 방지")
        void test1() {

        }

        // 2. 회원가입 기능 테스트
        @Test
        @Order(2)
        @DisplayName("회원 가입 기능 테스트")
        void test2() {

        }
    }

    @Nested
    @DisplayName("상품 테스트")
    class ProductTest {
        // 3. 가입된 회원의 관심 상품 등록 테스트
        @Test
        @Order(3)
        @DisplayName("관심 상품 등록")
        void test1() {

        }

        // 4. 관심상품 업데이트 테스트
        @Test
        @Order(4)
        @DisplayName("관심 상품 업데이트")
        void test2() {

        }

        // 5. 조회 테스트
        @Test
        @Order(5)
        @DisplayName("관심 상품 조회")
        void test3() {

        }
    }


}
