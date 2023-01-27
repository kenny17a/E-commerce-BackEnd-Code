package kennyStore.com.shoprite.dtos.request;

import kennyStore.com.shoprite.data.models.Address;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String username;
    private String password;
    private Address address;

}
