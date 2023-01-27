package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.repositories.AddressRepository;
import kennyStore.com.shoprite.dtos.request.CreateAddressRequest;
import kennyStore.com.shoprite.dtos.response.CreateAddressResponse;
import kennyStore.com.shoprite.services.AddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    private CreateAddressRequest createAddressRequest;

    @BeforeEach
    void setUp() {
        createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setStreetName("Hebert Macaulay Way");
        createAddressRequest.setStreetNumber("7");
        createAddressRequest.setCity("Lagos");
        createAddressRequest.setTown("Yaba");
    }

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }
    @Test
    void testThatAddressCanBeCreated(){
        CreateAddressResponse createAddressResponse = addressService.createAddress(createAddressRequest);
        assertNotNull(createAddressResponse.getId());
    }
}