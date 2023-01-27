package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.User;
import kennyStore.com.shoprite.dtos.request.*;
import kennyStore.com.shoprite.dtos.response.AddProductToCartResponse;
import kennyStore.com.shoprite.dtos.response.CreateUserResponse;
import kennyStore.com.shoprite.dtos.response.LoginResponse;
import kennyStore.com.shoprite.dtos.response.UpdateUserResponse;
import kennyStore.com.shoprite.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUserName(String username);
    List<User> getAllUsers();
    void deleteUserById(Long id);
    void updateUserRole(String username, Role role);
    UpdateUserResponse updateUserInfo(UpdateUserRequest updateUserRequest);
    LoginResponse login(LoginRequest loginRequest);
    AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest);
    String deleteAllUsers();
    String deleteProductFromCart(DeleteCartProductFromCartRequest deleteCartProductFromCartRequest);
}
