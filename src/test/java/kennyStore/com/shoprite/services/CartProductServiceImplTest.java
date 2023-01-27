package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.CartProduct;
import kennyStore.com.shoprite.dtos.request.UpdateCartProductRequest;
import kennyStore.com.shoprite.enums.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartProductServiceImplTest {
    @Autowired
    private CartProductService cartProductService;

    private CartProduct cartProduct1;
    private CartProduct cartProduct2;

    @BeforeEach
    void setUp() {
        cartProduct1 = new CartProduct();
        cartProduct1.setName("milk");
        cartProduct1.setQuantity(3);
        cartProduct1.setUnitPrice(BigDecimal.valueOf(10));
        cartProduct1.setTotalPrice(BigDecimal.valueOf(30));
        cartProduct1.setCategory(Category.FOOD);

        cartProduct2 = new CartProduct();
        cartProduct2.setName("Tv");
        cartProduct2.setQuantity(2);
        cartProduct2.setUnitPrice(BigDecimal.valueOf(250));
        cartProduct2.setTotalPrice(BigDecimal.valueOf(500));
        cartProduct2.setCategory(Category.ELECTRONICS);

    }

    @AfterEach
    void tearDown() {
        cartProductService.getAllCartProducts();
    }
    @Test
    void testThatCartProductCanBeAddedToCartProductDb(){
        assertNull(cartProduct1.getId());
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct1);
        assertNotNull(savedCartProduct.getId());
    }
    @Test
    void testThatIfOneCartProductsAreCreatedSizeOfCartProductDbIsOne(){
        cartProductService.createCartProduct(cartProduct1);
        int cartProductDbSize = cartProductService.getAllCartProducts().size();
        assertEquals(1, cartProductDbSize);
    }
    @Test
    void cartProductCanBeDeletedByIdOfProduct(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct1);
        cartProductService.createCartProduct(cartProduct2);
        int cartProductDbSizeBeforeDeletingAProduct = cartProductService.getAllCartProducts().size();
        assertEquals(2, cartProductDbSizeBeforeDeletingAProduct);
        cartProductService.deleteCartProductById(savedCartProduct.getId());
        int cartProductDbSizeAfterDeletingAProduct = cartProductService.getAllCartProducts().size();
        assertEquals(1, cartProductDbSizeAfterDeletingAProduct);
    }
    @Test
    void testThatCartProductCanBeFoundUsingCartProductId(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct1);
        CartProduct foundCartProduct = cartProductService.findCartProductById(savedCartProduct.getId());
        assertEquals(foundCartProduct.getName(), savedCartProduct.getName());
    }

    @Test
    void testThatCartProductQuantityCanBeUpdated(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct1);
        Integer quantityBeforeUpdate = cartProductService.findCartProductById(savedCartProduct.getId()).getQuantity();
        assertEquals(3, quantityBeforeUpdate);

        UpdateCartProductRequest updateCartProductRequest = new UpdateCartProductRequest();
        updateCartProductRequest.setCartProductId(savedCartProduct.getId());
        updateCartProductRequest.setQuantity(5);
        cartProductService.updateCartProduct(updateCartProductRequest);

        CartProduct foundCartProduct = cartProductService.findCartProductById(savedCartProduct.getId());
        assertEquals(5, foundCartProduct.getQuantity());
    }

    @Test
    void testWhenCartProductQuantityIsUpdated_TotalPriceOfQuantityIsAlsoUpdated(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct1);
        Integer quantityBeforeUpdate = cartProductService.findCartProductById(savedCartProduct.getId()).getQuantity();
        assertEquals(3, quantityBeforeUpdate);
        assertEquals(BigDecimal.valueOf(30), savedCartProduct.getTotalPrice());
    }

}