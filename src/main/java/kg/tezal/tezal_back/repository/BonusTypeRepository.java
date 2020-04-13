package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrgBonusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusTypeRepository extends JpaRepository<OrgBonusType, Long> {

}
