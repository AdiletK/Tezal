package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
