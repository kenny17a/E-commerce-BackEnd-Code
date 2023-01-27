package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.Product;
import kennyStore.com.shoprite.dtos.request.CreateProductRequest;
import kennyStore.com.shoprite.dtos.request.UpdateCartProductRequest;
import kennyStore.com.shoprite.dtos.response.CreateProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);
    Optional<Product> getProductByName(String name);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    String deleteAllProducts();
    String updateProduct(UpdateCartProductRequest updateCartProductRequest, Product product);
}
