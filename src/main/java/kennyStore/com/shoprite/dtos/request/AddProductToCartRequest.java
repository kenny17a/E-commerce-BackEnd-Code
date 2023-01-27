package kennyStore.com.shoprite.dtos.request;

import lombok.Data;

@Data
public class AddProductToCartRequest {
    private String username;
    private String productName;
    private Integer quantity;
}
