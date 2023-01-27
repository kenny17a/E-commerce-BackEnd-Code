package kennyStore.com.shoprite.data.repositories;

import kennyStore.com.shoprite.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressById(Long id);
}
