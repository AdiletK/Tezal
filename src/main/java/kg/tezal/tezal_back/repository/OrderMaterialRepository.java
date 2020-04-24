package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrderMaterial;
import kg.tezal.tezal_back.model.OrderMaterialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderMaterialRepository extends JpaRepository<OrderMaterial, Long> {
    @Query("select new kg.tezal.tezal_back.model.OrderMaterialModel(order_material.id, order_material.orders.id, order_material.numberOf, order_material.sumOf, order_material.rawMaterial.id, order_material.rawMaterial.name, order_material.rawMaterial.volume) FROM OrderMaterial order_material WHERE order_material.orders.id = :id  ORDER BY  order_material.orders.id ASC")
    List<OrderMaterialModel> findAllByOrderId(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.OrderMaterialModel(order_material.id, order_material.orders.id, order_material.numberOf, order_material.sumOf, order_material.rawMaterial.id, order_material.rawMaterial.name, order_material.rawMaterial.volume) FROM OrderMaterial order_material WHERE order_material.id = :id ")
    OrderMaterialModel getOrderMaterialById(@Param("id") Long id);
}
