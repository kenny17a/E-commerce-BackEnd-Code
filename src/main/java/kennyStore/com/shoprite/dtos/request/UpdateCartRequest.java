package kennyStore.com.shoprite.dtos.request;

import lombok.Data;

@Data
public class UpdateCartRequest {
    private Long cartProductId;
    private String cartProductName;
    private Integer quantity;
}
