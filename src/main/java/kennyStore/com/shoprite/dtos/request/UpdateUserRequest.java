package kennyStore.com.shoprite.dtos.request;

import kennyStore.com.shoprite.data.models.Address;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Address address;
}
