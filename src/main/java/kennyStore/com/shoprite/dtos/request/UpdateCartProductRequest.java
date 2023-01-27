package kennyStore.com.shoprite.dtos.request;

import kennyStore.com.shoprite.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCartProductRequest {
    private Long cartProductId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private Category category;
}
