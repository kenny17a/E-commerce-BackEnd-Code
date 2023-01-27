package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.Address;
import kennyStore.com.shoprite.dtos.request.CreateAddressRequest;
import kennyStore.com.shoprite.dtos.request.CreateProductRequest;
import kennyStore.com.shoprite.dtos.response.CreateAddressResponse;

public interface AddressService {
    CreateAddressResponse createAddress(CreateAddressRequest createAddressRequest);
    Address getAddressById(Long id);
    Address updateAddress(Address address, Long id);
}
