package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrgBonus;
import kg.tezal.tezal_back.model.BonusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgBonusRepository extends JpaRepository<OrgBonus, Long>, JpaSpecificationExecutor<BonusModel> {
    @Query("select new kg.tezal.tezal_back.model.BonusModel(orgBonus.id, orgBonus.orgBonusType, orgBonus.organization, orgBonus.status, orgBonus.validFrom, orgBonus.validTo, orgBonus.validity, orgBonus.createdDate) FROM OrgBonus orgBonus  ORDER BY orgBonus.id ASC")
    List<BonusModel> findAllOrgBonusList();

    @Query("select new kg.tezal.tezal_back.model.BonusModel(orgBonus.id, orgBonus.orgBonusType, orgBonus.organization, orgBonus.status, orgBonus.validFrom, orgBonus.validTo, orgBonus.validity, orgBonus.createdDate) FROM OrgBonus orgBonus where organization_id = :id  ORDER BY orgBonus.id ASC")
    List<BonusModel> findAllOrgBonusListByOrgId(@Param("id") Long id);
}
