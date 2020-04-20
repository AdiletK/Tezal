package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
