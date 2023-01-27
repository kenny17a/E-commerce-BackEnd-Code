package kennyStore.com.shoprite.dtos.request;

import lombok.Data;

@Data
public class DeleteCartProductFromCartRequest {
    private String username;
    private Long cartProductId;
}
