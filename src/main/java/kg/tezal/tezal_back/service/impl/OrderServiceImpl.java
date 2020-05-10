package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.ClientRestController;
import kg.tezal.tezal_back.apicontroller.OrganizationRestController;
import kg.tezal.tezal_back.apicontroller.UserRestController;
import kg.tezal.tezal_back.dao.ReportDao;
import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.model.OrderModel;
import kg.tezal.tezal_back.model.SalesShortModel;
import kg.tezal.tezal_back.repository.OrderRepository;
import kg.tezal.tezal_back.service.OrderService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrganizationRestController organizationRestController;
    private final UserRestController userRestController;
    private final ClientRestController clientRestController;
    @Autowired
    private ReportDao reportDao;

    public OrderServiceImpl(OrderRepository orderRepository, OrganizationRestController organizationRestController, UserRestController userRestController, ClientRestController clientRestController) {
        this.orderRepository = orderRepository;
        this.organizationRestController = organizationRestController;
        this.userRestController = userRestController;
        this.clientRestController = clientRestController;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public OrderModel getById(Long id) {
        return orderRepository.getOrderById(id);
    }


    @Override
    public List<OrderModel> findAllByOrgId(Long id) {
        return orderRepository.findAllByOrgId(id);
    }

    @Override
    public List<OrderModel> findAllByClientId(Long id) {
        return orderRepository.findAllByClientId(id);
    }

    @Override
    public List<SalesShortModel> findAllDeliveredByOrgIdWithDate(Long id, String dateFrom, String dateTo) {
        return reportDao.getSalesByOrgId(id, dateFrom, dateTo);
    }

    @Override
    public Page<OrderModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable) {
        return orderRepository.findAllOrdersByOrgIdAndByNameOrDescription(id, search, pageable);
    }

    @Override
    public Page<OrderModel> findAllByOrgIdAndByNameOrDescriptionManager(Long id, String search, Pageable pageable) {
        return orderRepository.findAllOrdersByOrgIdAndByNameOrDescriptionManager(id, search, pageable);
    }

    @Override
    public Page<OrderModel> findAllDeliveredByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable) {
        return orderRepository.findAllDeliveredOrdersByOrgIdAndByNameOrDescription(id, search, pageable);
    }

    @Override
    public Page<OrderModel> findAllByOrgIdAndByNameOrDescriptionForCashier(Long id, String search, Pageable pageable) {
        return orderRepository.findAllOrdersByOrgIdAndByNameOrDescriptionForCashier(id, search, pageable);
    }

    @Override
    public Page<OrderModel> findAllByUserIdAndByNameOrDescriptionForCashier(Long id, String search, Pageable pageable) {
        return orderRepository.findAllOrdersByUserIdAndByNameOrDescriptionForCashier(id, search, pageable);
    }

    @Override
    public Order create(OrderModel orderModel) {
        Order order = new Order();
        return getOrder(orderModel, order);
    }

    @Override
    public String deleteById(Long id) {
        orderRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public Order putById(Long id, OrderModel orderModel) {
        Order order = new Order();
        order.setId(id);
        return getOrder(orderModel, order);
    }

    private Order getOrder(OrderModel orderModel, Order order) {
        order.setClient(clientRestController.getClientById(orderModel.getClientId()));
        order.setOrdersStatus(orderModel.getOrdersStatus());
        order.setUser(orderModel.getUserId() != null ? userRestController.getUserById(orderModel.getUserId()) : null );
        order.setOrganization(organizationRestController.getOrganizationById(orderModel.getOrganizationId()));
        return orderRepository.save(order);
    }


}
