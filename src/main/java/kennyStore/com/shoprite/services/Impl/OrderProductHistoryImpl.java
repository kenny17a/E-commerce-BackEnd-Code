package kennyStore.com.shoprite.services.Impl;

import kennyStore.com.shoprite.data.models.OrderProductHistory;
import kennyStore.com.shoprite.data.repositories.OrderProductHistoryRepository;
import kennyStore.com.shoprite.services.OrderProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductHistoryImpl implements OrderProductHistoryService {
    @Autowired
    private OrderProductHistoryRepository orderProductHistoryRepository;
    @Override
    public OrderProductHistory save(OrderProductHistory orderProductHistory) {
        return orderProductHistoryRepository.save(orderProductHistory);
    }

    @Override
    public void deleteOrderHistories() {
        orderProductHistoryRepository.deleteAll();

    }

    @Override
    public List<OrderProductHistory> orderHistories() {
       return orderProductHistoryRepository.findAll();
    }
}
