package com.example.springcore.service;

import com.example.springcore.dto.ProductMypriceRequestDto;
import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.model.Product;
import com.example.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final int MIN_MY_PRICE = 100;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId) {

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);
        // 관심상품 저장
        productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long productId, ProductMypriceRequestDto requestDto) {

        // 관심 가격의 유효성 검증
        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        // 관심 가격 업데이트
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        product.setMyprice(myprice);
        productRepository.save(product);

        return product;
    }

    public List<Product> getProducts(Long userId) {
        List<Product> products = productRepository.findAllByUserId(userId);

        return products;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
