package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrderMaterial;
import kg.tezal.tezal_back.model.OrderMaterialModel;

import java.util.List;

public interface OrderMaterialService {
    OrderMaterial findById(Long id);

    OrderMaterialModel getById(Long id);

    List<OrderMaterialModel> findAllByOrgId(Long id);

    OrderMaterial create(OrderMaterialModel rateModel);

    OrderMaterial putById(Long id, OrderMaterialModel rateModel);

    String deleteById(Long id);

}