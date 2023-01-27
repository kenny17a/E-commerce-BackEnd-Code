package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.CartProduct;
import kennyStore.com.shoprite.dtos.request.UpdateCartProductRequest;
import kennyStore.com.shoprite.dtos.request.UpdateCartRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CartProductService {
    CartProduct createCartProduct(CartProduct cartProduct);
    void deleteCartProductById(Long id);
    List<CartProduct> getAllCartProducts();
    String deleteAllCartProducts();
    CartProduct updateCartProduct(UpdateCartProductRequest updateCartProductRequest);
    CartProduct findCartProductById(Long cartProductId);
    List<CartProduct> getCartProductsByName(String name);
    void updateListOfCartProducts(String name, BigDecimal price);
}
