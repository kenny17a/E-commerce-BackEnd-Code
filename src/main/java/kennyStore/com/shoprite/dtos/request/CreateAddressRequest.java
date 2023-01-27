package kennyStore.com.shoprite.dtos.request;

import lombok.Data;

@Data
public class CreateAddressRequest {
    private String streetName;
    private String streetNumber;
    private String city;
    private String town;
}
