package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order findById(Long id);

    OrderModel getById(Long id);

    List<OrderModel> findAllByOrgId(Long id);

    Page<OrderModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Order create(OrderModel rateModel);

    String deleteById(Long id);

    Order putById(Long id, OrderModel rateModel);

}