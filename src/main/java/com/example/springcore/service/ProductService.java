package com.example.springcore.service;

import com.example.springcore.dto.ProductMypriceRequestDto;
import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.model.Product;
import com.example.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

    public Product createProduct(ProductRequestDto requestDto, Long userId) throws SQLException {

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);

        productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        product.setMyprice(myprice);
        productRepository.save(product);

        return product;
    }

    public List<Product> getProducts(Long userId) throws SQLException {
        List<Product> products = productRepository.findAllByUserId(userId);

        return products;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
