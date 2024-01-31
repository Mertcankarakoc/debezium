package com.debezium.uygulama.service;

import com.debezium.uygulama.model.Product;
import com.debezium.uygulama.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId,Product updateProduct){
        final Product currentProduct = productRepository.findById(productId).orElse(null);
        currentProduct.setName(updateProduct.getName());
        currentProduct.setPrice(updateProduct.getPrice());
        currentProduct.setStock(updateProduct.getStock());
        return productRepository.save(currentProduct);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
