package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.model.OrderModel;
import kg.tezal.tezal_back.model.SalesShortModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order findById(Long id);

    OrderModel getById(Long id);

    List<OrderModel> findAllByOrgId(Long id);

    List<OrderModel> findAllByClientId(Long id);

    List<OrderModel> findByOrgIdAndTypeWithDate(Long id, String type, String dateFrom, String dateTo);

    List<SalesShortModel> findAllDeliveredByOrgIdWithDate(Long id, String dateFrom, String dateTo);

    Page<OrderModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Page<OrderModel> findAllByOrgIdAndByNameOrDescriptionManager(Long id, String search, Pageable pageable);

    Page<OrderModel> findAllDeliveredByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Page<OrderModel> findAllByOrgIdAndByNameOrDescriptionForCashier(Long id, String search, Pageable pageable);

    Page<OrderModel> findAllByUserIdAndByNameOrDescriptionForCashier(Long id, String search, Pageable pageable);

    Order create(OrderModel rateModel);

    String deleteById(Long id);

    Order putById(Long id, OrderModel rateModel);

}