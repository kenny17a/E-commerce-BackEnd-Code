package kennyStore.com.shoprite.dtos.response;

import lombok.Data;

@Data
public class CreateUserResponse {
    private Long id;
    private String username;
    private String email;
    private String message;

}
