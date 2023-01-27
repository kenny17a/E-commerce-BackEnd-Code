package kennyStore.com.shoprite.services;

import kennyStore.com.shoprite.data.models.OrderProductHistory;

import java.util.List;

public interface OrderProductHistoryService {
    OrderProductHistory save(OrderProductHistory orderProductHistory);
    void deleteOrderHistories();
    List<OrderProductHistory> orderHistories();
}
