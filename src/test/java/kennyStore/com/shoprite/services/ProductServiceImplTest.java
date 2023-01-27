package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.Address;
import kennyStore.com.shoprite.data.models.Product;
import kennyStore.com.shoprite.data.repositories.ProductRepository;
import kennyStore.com.shoprite.dtos.request.*;
import kennyStore.com.shoprite.dtos.response.CreateProductResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static kennyStore.com.shoprite.enums.Category.BEVERAGES;
import static kennyStore.com.shoprite.enums.Category.FOOD;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartProductService cartProductService;
    CreateProductResponse createProductResponse ;
    CreateProductRequest createProductRequest;

    @BeforeEach
    void setUp() {
        createProductResponse = new CreateProductResponse();
        createProductRequest = new CreateProductRequest();
        createProductRequest.setName("Noodles");
        createProductRequest.setCategory(FOOD);
        createProductRequest.setPrice(BigDecimal.valueOf(120));
        createProductRequest.setQuantity(4);

    }
    @AfterEach
    void afterEach(){
//        productRepository.deleteAll();
        userService.deleteAllUsers();
        cartProductService.deleteAllCartProducts();
    }
    @Test
    void productCanBeCreated(){
        createProductResponse = productService.createProduct(createProductRequest);
        System.out.println(createProductResponse);
        assertNotNull(createProductResponse.getId());
    }
    @Test
    void productCanBeSearchedForUsingProductName(){
        productService.createProduct(createProductRequest);
        Optional<Product> foundProduct = productService.getProductByName("Noodles");
        assertNotNull(foundProduct);
    }
    @Test
    void productCanBeSearchedForById(){
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);
        Product foundProduct = productService.getProductById(createProductResponse.getId());
        assertNotNull(foundProduct);
    }
    @Test
    void testThatListOfProductsIsEqualToTheNumberOfProductAdded(){
        var productInDbSizeBeforeAddingProduct = productService.getAllProducts();
        assertEquals(0, productInDbSizeBeforeAddingProduct.size());
        productService.createProduct(createProductRequest);
        var productDbAfterAddingProduct = productService.getAllProducts();
        assertEquals(1, productDbAfterAddingProduct.size());
    }
    @Test
    void testThatProductCanBeUpdated(){
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);

        Product foundProductBeforeUpdate = productService.getProductById(createProductResponse.getId());

        assertEquals(4, foundProductBeforeUpdate.getQuantity());
        assertEquals(new BigDecimal("120.00"), foundProductBeforeUpdate.getUnitPrice());

        UpdateCartProductRequest updateCartProductRequest = new UpdateCartProductRequest();

        updateCartProductRequest.setProductName("Milk");
        updateCartProductRequest.setCategory(BEVERAGES);
        updateCartProductRequest.setQuantity(5);
        updateCartProductRequest.setPrice(BigDecimal.valueOf(175.0));

        productService.updateProduct(updateCartProductRequest, foundProductBeforeUpdate);
        Product foundProductAfterUpdate = productService.getProductById(createProductResponse.getId());
        assertEquals(5, foundProductAfterUpdate.getQuantity());
        assertEquals(new BigDecimal("175.00"), foundProductAfterUpdate.getUnitPrice());

    }
    @Test
    void testThatWhenAProductIsUpdatedProductsWithSameNameInUsersCartGetUpdated(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        Address address = new Address();
        createUserRequest.setUsername("Daniel");
        createUserRequest.setPassword("0913");
        createUserRequest.setEmail("danielkenny@gmail.com");
        createUserRequest.setAddress(address);
        userService.createUser(createUserRequest);
        productService.createProduct(createProductRequest);

        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
        addProductToCartRequest.setUsername("Daniel");
        addProductToCartRequest.setProductName("juice");
        addProductToCartRequest.setQuantity(6);
        userService.addProductToCart(addProductToCartRequest);

        var cartProductsBeforeUpdate = cartProductService.getCartProductsByName("juice");
        assertEquals(new BigDecimal("120.00"), cartProductsBeforeUpdate.get(0).getUnitPrice());
        assertEquals(new BigDecimal("720.00"), cartProductsBeforeUpdate.get(0).getTotalPrice());
        assertEquals(1, cartProductsBeforeUpdate.size());

        UpdateCartProductRequest updateCartProductRequest = new UpdateCartProductRequest();
        updateCartProductRequest.setProductName("milk");
        updateCartProductRequest.setPrice(BigDecimal.valueOf(60));
        productService.updateProduct(updateCartProductRequest, new Product());
        var cartProductsAfterUpdate = cartProductService.getCartProductsByName("milk");
        assertEquals(new BigDecimal("50.00"), cartProductsAfterUpdate.get(0).getUnitPrice());
        assertEquals(new BigDecimal("100.00"), cartProductsAfterUpdate.get(0).getTotalPrice());
        assertEquals(1, cartProductsAfterUpdate.size());


    }
}