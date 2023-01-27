package kennyStore.com.shoprite.data.repositories;

import kennyStore.com.shoprite.data.models.OrderProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductHistoryRepository extends JpaRepository<OrderProductHistory, Long> {
}
