package kennyStore.com.shoprite.dtos.response;

import lombok.Data;

@Data
public class AddProductToCartResponse {
    private String name;
    private Long id;
    private final String message = name + " add to cart";

}
