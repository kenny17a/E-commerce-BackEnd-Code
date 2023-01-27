package kennyStore.com.shoprite.dtos.request;

import kennyStore.com.shoprite.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private Category category;
    private BigDecimal price;
    private Integer quantity;
}
