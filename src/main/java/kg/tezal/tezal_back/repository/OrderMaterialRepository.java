package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Order_Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMaterialRepository extends JpaRepository<Order_Material, Long> {
}
