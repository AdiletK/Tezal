package kg.tezal.tezal_back.repository;


import kg.tezal.tezal_back.entity.ClientPreferenceValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPreferenceValueRepository extends JpaRepository<ClientPreferenceValue, Long> {
}
