package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.ClientEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientEventRepository extends JpaRepository<ClientEvent, Long> {
}
