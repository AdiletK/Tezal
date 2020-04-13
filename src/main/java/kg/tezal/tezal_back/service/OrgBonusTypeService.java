package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrgBonusType;
import kg.tezal.tezal_back.model.OrgBonusTypeModel;

import java.util.List;

public interface OrgBonusTypeService {
    OrgBonusType findById(Long id);

    List<OrgBonusType> findAll();

    OrgBonusType create(OrgBonusType orgBonusType);

    String deleteById(Long id);

    OrgBonusType putById(Long id, OrgBonusType orgBonusType);

    List<OrgBonusTypeModel> findAllOrgBonusTypeDto();
}
