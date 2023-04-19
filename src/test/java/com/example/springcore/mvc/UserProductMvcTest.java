package com.example.springcore.mvc;

import com.example.springcore.controller.ProductController;
import com.example.springcore.controller.UserController;
import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.model.User;
import com.example.springcore.model.UserRoleEnum;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.security.WebSecurityConfig;
import com.example.springcore.service.KakaoUserService;
import com.example.springcore.service.ProductService;
import com.example.springcore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// MVC 테스트는 MODEL, VIEW, CONTROLLER를 모두 테스트합니다.
@WebMvcTest(
        // 컨트롤러를 하나로 합쳤습니다. (권장되지 않습니다.)
        controllers = {UserController.class, ProductController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class UserProductMvcTest {
    private MockMvc mvc; // 테스트를 진행할 객체입니다.

    private Principal mockPrincipal; //로그인한 사용자로 인식할 가짜 객체입니다.

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;


    // 기존 service 객체의 DI를 끊어주고 대신 Mock 객체로 전환해줍니다. "했다 치고" 기능을 제공합니다.
    @MockBean
    UserService userService;

    @MockBean
    KakaoUserService kakaoUserService;

    @MockBean
    ProductService productService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter())) // testImplementation springsecurity에 포함되어 있습니다.
                .build();
    }

    private void mockUserSetup() {
// Mock 테스트 유져 생성
        String username = "제이홉";
        String password = "hope!@#";
        String email = "hope@sparta.com";
        UserRoleEnum role = UserRoleEnum.USER;
        User testUser = new User(username, password, email, role);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);

        // 가짜 로그인 사용자 정책 생성
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("로그인 view")
    void test1() throws Exception {
// when - then
        // .perform은 http요청을 보내는 상황을 보여주고 있습니다.
        mvc.perform(get("/user/login")) // 로그인 화면을 보여주는 get 요청
                .andExpect(status().isOk()) // 200 코드가 나와야 합니다.
                .andExpect(view().name("login")) // 로그인 view를 반환해야 합니다.
                .andDo(print()); // 위 결과를 출력합니다.
        // userController를 보면 service가 수행되고 있으나 우리는 이전에 mock객체로 전환시키면서 di를 끊어주었기 때문에 "했다 치고" 넘어갑니다.
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test2() throws Exception {
// given
        // 회원 가입에 필요한 폼 데이터를 생성하겠습니다.
        MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
        signupRequestForm.add("username", "제이홉");
        signupRequestForm.add("password", "hope!@#");
        signupRequestForm.add("email", "hope@sparta.com");
        signupRequestForm.add("admin", "false");

// when - then
        mvc.perform(post("/user/signup") // 회원가입 http 요청입니다.
                        .params(signupRequestForm) // 폼 데이터를 @RequestParam에 전달해줍니다.
                ) // 역시 USERSERVICE 부분은 mock으로 등록되어 했다 치고 넘어감
                .andExpect(status().is3xxRedirection()) // redirection을 하고 있기 때문에 status code는 3xx redirection입니다.
                .andExpect(view().name("redirect:/user/login")) // view name을 확인합니다.
                .andDo(print()); // 결과를 출력합니다.
    }

    @Test
    @DisplayName("신규 관심상품 등록")
    void test3() throws Exception {
// given
        this.mockUserSetup(); // 로그인한 유저인지 검사하고 있기 때문에 가짜 사용자 등록을 진행합니다.
        // 상품 객체 입니다.
        String title = "Apple <b>에어팟</b> 2세대 유선충전 모델 (MV7N2KH/A)";
        String imageUrl = "https://shopping-phinf.pstatic.net/main_1862208/18622086330.20200831140839.jpg";
        String linkUrl = "https://search.shopping.naver.com/gate.nhn?id=18622086330";
        int lPrice = 77000;
        ProductRequestDto requestDto = new ProductRequestDto(
                title,
                imageUrl,
                linkUrl,
                lPrice
        );
        // json 형태의 문자열 데이터로 변환합니다. objectMapper를 활용합니다. java object -> json type string (json)
        String postInfo = objectMapper.writeValueAsString(requestDto);

// when - then
        mvc.perform(post("/api/products")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal) // mock 정책을 같이 보내줘서 로그인 사용자로 인
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
