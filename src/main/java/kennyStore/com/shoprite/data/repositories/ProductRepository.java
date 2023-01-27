package kennyStore.com.shoprite.data.repositories;

import kennyStore.com.shoprite.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByProductNameIgnoreCase(String name);
}
