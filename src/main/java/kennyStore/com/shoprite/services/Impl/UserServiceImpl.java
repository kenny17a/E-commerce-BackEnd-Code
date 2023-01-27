package kennyStore.com.shoprite.services.Impl;

import jakarta.transaction.Transactional;
import kennyStore.com.shoprite.data.models.Cart;
import kennyStore.com.shoprite.data.models.CartProduct;
import kennyStore.com.shoprite.data.models.Product;
import kennyStore.com.shoprite.data.models.User;
import kennyStore.com.shoprite.data.repositories.UserRepository;
import kennyStore.com.shoprite.dtos.request.*;
import kennyStore.com.shoprite.dtos.response.AddProductToCartResponse;
import kennyStore.com.shoprite.dtos.response.CreateUserResponse;
import kennyStore.com.shoprite.dtos.response.LoginResponse;
import kennyStore.com.shoprite.dtos.response.UpdateUserResponse;
import kennyStore.com.shoprite.enums.Role;
import kennyStore.com.shoprite.exceptions.ProductException;
import kennyStore.com.shoprite.exceptions.UserException;
import kennyStore.com.shoprite.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductService productService;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private OrderProductHistoryService orderProductHistoryService;
    @Autowired
    private AddressService addressService;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.findByUsername(createUserRequest.getUsername().toLowerCase()).isPresent())
            throw new UserException("User already exist");
        if (userRepository.findUserByEmail(createUserRequest.getEmail().toLowerCase()).isPresent())
            throw new UserException("User with this email already exist");
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setAddress(createUserRequest.getAddress());
        user.setCart(new Cart());
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMessage("User successfully created");
        createUserResponse.setId(savedUser.getId());
        return createUserResponse;

    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (userRepository.findById(id).isEmpty())throw new UserException("User does not exist");
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        if (userRepository.findByUsername(username.toLowerCase()).isEmpty())
            throw new UserException("User does not exist");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        getUserById(id);
        userRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void updateUserRole(String username, Role role) {
        userRepository.updateUserRole(username, role);
    }

    @Override
    public UpdateUserResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        Optional<User> foundUser = getUserByUserName(updateUserRequest.getUsername());
        if (updateUserRequest.getFirstName() != null)foundUser.ifPresent(user -> user.setFirstName(updateUserRequest.getFirstName()));
        if (updateUserRequest.getLastName() != null)foundUser.ifPresent(user -> user.setLastName(updateUserRequest.getLastName()));
        if (updateUserRequest.getAddress() != null)foundUser.ifPresent(user -> user.setAddress(updateUserRequest.getAddress()));
        userRepository.save(foundUser.get());
        return new UpdateUserResponse();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> foundUser = getUserByUserName(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        if (Objects.equals(loginRequest.getPassword(), foundUser.get().getPassword())){
        loginResponse.setMessage("Login Successful");
        }else loginResponse.setMessage("Login unsuccessful");
        return loginResponse;
    }


    @Override
    public AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest) {

        User foundUser = getUserByUserName(addProductToCartRequest.getUsername())
                .orElseThrow(()-> new ProductException("User Not found"));
        Product foundProduct = productService.getProductByName(addProductToCartRequest.getProductName())
                .orElseThrow(()-> new ProductException("Product Not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setName(foundProduct.getProductName());
        cartProduct.setCategory(foundProduct.getCategory());
        cartProduct.setQuantity(foundProduct.getQuantity());
        cartProduct.setUnitPrice(foundProduct.getUnitPrice());
        cartProduct.setTotalPrice(foundProduct.getUnitPrice().multiply(new BigDecimal(cartProduct.getQuantity())));

        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct);

        foundUser.getCart().getCartProducts().add(savedCartProduct);
        AddProductToCartResponse addProductToCartResponse = new AddProductToCartResponse();
        addProductToCartResponse.setName(addProductToCartRequest.getProductName());
        addProductToCartResponse.setId(savedCartProduct.getId());
        return addProductToCartResponse;
    }

    @Override
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "Users deleted successfully";
    }

    @Override
    public String deleteProductFromCart(DeleteCartProductFromCartRequest deleteCartProductFromCartRequest) {
        cartProductService.deleteCartProductById(deleteCartProductFromCartRequest.getCartProductId());
        return "Deleted Successfully";
    }
}
