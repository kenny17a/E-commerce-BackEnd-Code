package kennyStore.com.shoprite.services.Impl;

import kennyStore.com.shoprite.data.models.Address;
import kennyStore.com.shoprite.data.repositories.AddressRepository;
import kennyStore.com.shoprite.dtos.request.CreateAddressRequest;
import kennyStore.com.shoprite.dtos.request.CreateProductRequest;
import kennyStore.com.shoprite.dtos.response.CreateAddressResponse;
import kennyStore.com.shoprite.services.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public CreateAddressResponse createAddress(CreateAddressRequest createAddressRequest) {
        Address address = Address.builder().
                streetName(createAddressRequest.getStreetName()).
                streetNumber(createAddressRequest.getStreetNumber()).
                city(createAddressRequest.getCity()).
                town(createAddressRequest.getTown()).
                build();
        Address savedAddress = addressRepository.save(address);
        CreateAddressResponse createAddressResponse = new CreateAddressResponse();
        createAddressResponse.setId(savedAddress.getId());
        createAddressResponse.setMessage("Address added successfully");
        return createAddressResponse;
    }

    @Override
    public Address getAddressById(Long id) {
        Address foundAddress = addressRepository.findAddressById(id);
        return foundAddress;
    }

    @Override
    public Address updateAddress(Address address, Long id) {
        Address foundAddress = getAddressById(id);
        foundAddress.setStreetName(address.getStreetName());
        foundAddress.setStreetNumber(address.getStreetNumber());
        foundAddress.setCity(address.getCity());
        foundAddress.setTown(address.getTown());
        Address updatedAddress = addressRepository.save(foundAddress);
        return updatedAddress;
    }
}
