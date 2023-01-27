package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.Address;
import kennyStore.com.shoprite.data.models.Product;
import kennyStore.com.shoprite.data.models.User;
import kennyStore.com.shoprite.dtos.request.*;
import kennyStore.com.shoprite.dtos.response.AddProductToCartResponse;
import kennyStore.com.shoprite.dtos.response.CreateUserResponse;
import kennyStore.com.shoprite.dtos.response.LoginResponse;
import kennyStore.com.shoprite.services.Impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static kennyStore.com.shoprite.enums.Category.FOOD;
import static kennyStore.com.shoprite.enums.Role.ADMIN;
import static kennyStore.com.shoprite.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartProductService cartProductService;

    private CreateUserResponse createUserResponse;
    private CreateUserRequest createUserRequest;
    private CreateProductRequest createProductRequest;



    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setCity("Lagos");
        address.setTown("Yaba");
        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("kenny@gmail.com");
        createUserRequest.setUsername("kenny");
        createUserRequest.setPassword("kenn123");
        createUserRequest.setAddress(address);

        createProductRequest = new CreateProductRequest();
        createProductRequest.setName("Noodles");
        createProductRequest.setCategory(FOOD);
        createProductRequest.setPrice(BigDecimal.valueOf(120));
        createProductRequest.setQuantity(4);

    }

    @AfterEach
    void tearDown() {
        userService.deleteAllUsers();
        productService.deleteAllProducts();
        cartProductService.deleteAllCartProducts();

    }
    @Test
    void testThat_UserCanBe_CreatedAnd_Id_Can_Be_Generated(){
        createUserResponse = userService.createUser(createUserRequest);
        System.out.println(createUserResponse);
        assertNotNull(createUserResponse.getId());

    }
    @Test
    void userCanBeFindByIdTest(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(createUserResponse.getId());
        assertTrue(foundUser.isPresent());
    }
    @Test
    void testThatAddressIsCreatedWhenAUserIsCreated(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(createUserResponse.getId());
        assertNotNull(foundUser.get().getAddress().getId());
    }
    @Test
    void testThatCartIsCreatedWhenAUserIsCreated(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(createUserResponse.getId());
        assertNotNull(foundUser.get().getCart().getId());
    }
    @Test
    void testThatUserCanBeFindByUserName(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUserName("kenny");
        assertTrue(foundUser.isPresent());
    }
    @Test
    void listOfUserIsEqualsNumberOfUserSavedInUserDb(){
        createUserResponse = userService.createUser(createUserRequest);
        var users = userService.getAllUsers();
        assertEquals(1, users.size());
    }
    @Test
    void userDbSizeIsZeroIfTheOnlyUserInItIsDeleted(){
        createUserResponse = userService.createUser(createUserRequest);
        var usersBeforeDeleting = userService.getAllUsers();
        assertEquals(1, usersBeforeDeleting.size());
        userService.deleteUserById(createUserResponse.getId());
        var usersAfterDeleting = userService.getAllUsers();
        assertEquals(0, usersAfterDeleting.size());
    }
    @Test
    void aUserWithUserRoleCanBeUpdatedToAdmin(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUserBeforeUpdate = userService.getUserByUserName("kenny");
        assertEquals(USER, foundUserBeforeUpdate.get().getRole());
        userService.updateUserRole("kenny", ADMIN);
        Optional<User> foundUserAfterUpdate = userService.getUserByUserName("kenny");
        System.out.println(foundUserAfterUpdate);
        assertEquals(ADMIN, foundUserAfterUpdate.get().getRole());

    }
    @Test
    void testThatUserDetailsCanBeUpdated(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUserBeforeUpdate = userService.getUserByUserName("kenny");
        assertNull(foundUserBeforeUpdate.get().getFirstName());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("kenny");
        updateUserRequest.setFirstName("mayor");
        userService.updateUserInfo(updateUserRequest);
        Optional<User> foundUserAfterUpdate = userService.getUserByUserName("kenny");
        assertEquals("mayor", foundUserAfterUpdate.get().getFirstName());

    }
    @Test
    void userCanLoginIfLoginDetailsAreFoundInDb(){
        createUserResponse = userService.createUser(createUserRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("kenny");
        loginRequest.setPassword("kenn123");
        LoginResponse loginResponse = new LoginResponse();
        assertEquals("Login Successful", loginResponse.getMessage());
    }
    @Test
    void testThatNumberOfProductsInTheCartIsEqualToTheNumberOfProductsAddedToTheCart(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUserName("kenny");
        var numberOfProductsInTheUserCartBeforeAddingProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInTheUserCartBeforeAddingProduct);

        productService.createProduct(createProductRequest);
        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();

        addProductToCartRequest.setUsername("kenny");
        addProductToCartRequest.setProductName("Noodles");
        addProductToCartRequest.setQuantity(4);
        userService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUserName("kenny");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size());
        var numberOfProductsInTheCartAfterAddingAProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1, numberOfProductsInTheCartAfterAddingAProduct);
        assertEquals(1, cartProductService.getAllCartProducts().size());
    }
    @Test
    void testThatNumberOfProductsInAUserCartDecreasesByOneIfAProductIsDeleted(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUserName("kenny");
        var numberOfProductsInUserCartBeforeAddingAProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartBeforeAddingAProduct);

        productService.createProduct(createProductRequest);
        Optional<Product> foundProduct = productService.getProductByName("Noodles");

        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
        addProductToCartRequest.setUsername("kenny");
        addProductToCartRequest.setProductName("Noodles");
        addProductToCartRequest.setQuantity(4);

        AddProductToCartResponse addProductToCartResponse = userService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUserName("kenny");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart().getCartProducts());
        var numberOfProductsInUserCartAfterAddingProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1,numberOfProductsInUserCartAfterAddingProduct);

        DeleteCartProductFromCartRequest deleteCartProductFromCartRequest = new DeleteCartProductFromCartRequest();
        deleteCartProductFromCartRequest.setUsername("kenny");
        deleteCartProductFromCartRequest.setCartProductId(addProductToCartResponse.getId());

        userService.deleteProductFromCart(deleteCartProductFromCartRequest);

        Optional<User> foundUserAfterDeletingProductInCart = userService.getUserByUserName("kenny");
        var numberOfProductsInUserAfterDeletingAProduct = foundUserAfterDeletingProductInCart.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserAfterDeletingAProduct);
        assertEquals(0, cartProductService.getAllCartProducts().size());
    }


}