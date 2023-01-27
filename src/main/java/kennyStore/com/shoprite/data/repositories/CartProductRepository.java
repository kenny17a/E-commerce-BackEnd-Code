package kennyStore.com.shoprite.data.repositories;

import kennyStore.com.shoprite.data.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    void deleteCartProductById(Long id);
    CartProduct findCartProductById(Long id);
    List<CartProduct> getByName(String cartProductName);
}
