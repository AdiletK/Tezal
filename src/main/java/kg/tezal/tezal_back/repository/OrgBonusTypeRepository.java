package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrgBonusType;
import kg.tezal.tezal_back.model.OrgBonusTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgBonusTypeRepository extends JpaRepository<OrgBonusType, Long> {
    @Query("select new kg.tezal.tezal_back.model.OrgBonusTypeModel(orgBonusType.id, orgBonusType.name, orgBonusType.valueType,orgBonusType.description) FROM OrgBonusType orgBonusType")
    List<OrgBonusTypeModel> findAllOrgBonusTypeDto();
}