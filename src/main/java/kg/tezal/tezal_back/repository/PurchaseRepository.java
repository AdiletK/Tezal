package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
