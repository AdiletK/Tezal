package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrgBonusValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgBonusValueRepository extends JpaRepository<OrgBonusValue, Long> {
}
