package kennyStore.com.shoprite.dtos.response;

import lombok.Data;

@Data
public class CreateProductResponse {
    private Long id;
    private String name;
    private final String message = name + "added successfully";
}
