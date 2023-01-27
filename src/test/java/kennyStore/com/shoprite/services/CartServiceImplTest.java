package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.Address;
import kennyStore.com.shoprite.data.models.User;
import kennyStore.com.shoprite.data.repositories.CartProductRepository;
import kennyStore.com.shoprite.dtos.request.AddProductToCartRequest;
import kennyStore.com.shoprite.dtos.request.CreateProductRequest;
import kennyStore.com.shoprite.dtos.request.CreateUserRequest;
import kennyStore.com.shoprite.enums.Category;
import kennyStore.com.shoprite.services.CartService;
import kennyStore.com.shoprite.services.ProductService;
import kennyStore.com.shoprite.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceImplTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    private CreateUserRequest createUserRequest;
    private AddProductToCartRequest addProductToCartRequest;
    private CreateProductRequest createProductRequest;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setAddress(address);
        createUserRequest.setEmail("kenny@gmail.com");
        createUserRequest.setPassword("alpha123");
        createUserRequest.setUsername("kenny");

        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setName("noodles");
        createProductRequest.setCategory(Category.FOOD);
        createProductRequest.setPrice(BigDecimal.valueOf(120));
        createProductRequest.setQuantity(5);

    }

    @AfterEach
    void tearDown() {
        cartProductRepository.deleteAll();
        userService.deleteAllUsers();
        productService.deleteAllProducts();
    }
    @Test
    void productCanBeAddedToAUserCart(){
        userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUserName("kenny");
        var numberOfProductsInsideCartBeforeAddingAProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInsideCartBeforeAddingAProduct);
    }
}