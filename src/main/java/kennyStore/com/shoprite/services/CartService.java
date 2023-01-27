package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.dtos.request.AddProductToCartRequest;
import kennyStore.com.shoprite.dtos.response.AddProductToCartResponse;

public interface CartService {
    AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest);
    String deleteProductFromCart(Long cardProductId);
}
