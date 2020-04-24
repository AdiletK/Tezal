package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select new kg.tezal.tezal_back.model.OrderModel(orders.id, orders.ordersStatus, orders.user.id, orders.client.id, orders.client.firstName, orders.client.lastName, orders.organization.id, orders.organization.name, orders.updateDate) FROM Order orders WHERE organization_id = :id  ORDER BY  organization_id ASC")
    List<OrderModel> findAllByOrgId(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.OrderModel(orders.id, orders.ordersStatus, orders.user.id, orders.client.id, orders.client.firstName, orders.client.lastName, orders.organization.id, orders.organization.name, orders.updateDate) FROM Order orders WHERE orders.id = :id ")
    OrderModel getOrderById(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.OrderModel(orders.id, orders.ordersStatus, orders.user.id, orders.client.id, orders.client.firstName, orders.client.lastName, orders.organization.id, orders.organization.name, orders.updateDate) FROM Order orders WHERE " +
            "orders.organization.id = :id and (lower(orders.client.firstName) like %:search% or lower(orders.client.lastName) like %:search% ) ORDER BY orders.id ASC")
    Page<OrderModel> findAllOrdersByOrgIdAndByNameOrDescription(@Param("id") Long id, String search, Pageable pageable);


}
