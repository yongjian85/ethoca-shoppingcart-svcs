package com.yxj.ethoca.services;

import com.yxj.ethoca.Repositories.ProductsRepository;
import com.yxj.ethoca.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Product> getProducts() {

        return productsRepository.getProducts();
    }
}
