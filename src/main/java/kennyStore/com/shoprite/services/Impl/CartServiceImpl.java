package kennyStore.com.shoprite.services.Impl;

import kennyStore.com.shoprite.data.models.CartProduct;
import kennyStore.com.shoprite.data.models.Product;
import kennyStore.com.shoprite.data.models.User;
import kennyStore.com.shoprite.dtos.request.AddProductToCartRequest;
import kennyStore.com.shoprite.dtos.response.AddProductToCartResponse;
import kennyStore.com.shoprite.services.CartService;
import kennyStore.com.shoprite.services.ProductService;
import kennyStore.com.shoprite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Override
    public AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest) {
        Optional<User> foundUser = userService.getUserByUserName(addProductToCartRequest.getUsername());
        Optional<Product> foundProduct = productService.getProductByName(addProductToCartRequest.getProductName().toLowerCase());
        //Todo: create a model for cart product

        CartProduct cartProduct = new CartProduct();
        cartProduct.setName(foundProduct.get().getProductName());
        cartProduct.setCategory(foundProduct.get().getCategory());
        cartProduct.setQuantity(foundProduct.get().getQuantity());
        cartProduct.setUnitPrice(foundProduct.get().getUnitPrice());
        cartProduct.setTotalPrice(foundProduct.get().getUnitPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
        foundUser.get().getCart().getCartProducts().add(cartProduct);
        return null;
    }

    @Override
    public String deleteProductFromCart(Long cardProductId) {
        return null;
    }
}
