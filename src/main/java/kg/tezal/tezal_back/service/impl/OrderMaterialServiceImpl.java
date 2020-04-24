package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.OrderRestController;
import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.entity.OrderMaterial;
import kg.tezal.tezal_back.model.OrderMaterialModel;
import kg.tezal.tezal_back.repository.OrderMaterialRepository;
import kg.tezal.tezal_back.service.OrderMaterialService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMaterialServiceImpl implements OrderMaterialService {
    private final OrderMaterialRepository orderMaterialRepository;
    private final RawMaterialRestController rawMaterialRestController;
    private final OrderRestController orderRestController;

    public OrderMaterialServiceImpl(OrderMaterialRepository orderMaterialRepository, RawMaterialRestController rawMaterialRestController, OrderRestController orderRestController) {
        this.orderMaterialRepository = orderMaterialRepository;
        this.rawMaterialRestController = rawMaterialRestController;
        this.orderRestController = orderRestController;
    }


    @Override
    public OrderMaterial findById(Long id) {
        return orderMaterialRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public OrderMaterialModel getById(Long id) {
        return orderMaterialRepository.getOrderMaterialById(id);
    }


    @Override
    public List<OrderMaterialModel> findAllByOrgId(Long id) {
        return orderMaterialRepository.findAllByOrderId(id);
    }


    @Override
    public OrderMaterial create(OrderMaterialModel orderModel) {
        OrderMaterial order = new OrderMaterial();
        return getOrderMaterial(orderModel, order);
    }

    @Override
    public OrderMaterial putById(Long id, OrderMaterialModel orderModel) {
        OrderMaterial order = new OrderMaterial();
        order.setId(id);
        return getOrderMaterial(orderModel, order);
    }

    @Override
    public String deleteById(Long id) {
        orderMaterialRepository.deleteById(id);
        return "Record deleted";
    }

    private OrderMaterial getOrderMaterial(OrderMaterialModel orderModel, OrderMaterial order) {
        order.setNumberOf(orderModel.getCount());
        order.setSumOf(orderModel.getSum());
        order.setRawMaterial(rawMaterialRestController.getRawMaterialById(orderModel.getRawMaterialId()));
        order.setOrders(orderRestController.getOrderById(orderModel.getOrderId()));
        return orderMaterialRepository.save(order);
    }



}
