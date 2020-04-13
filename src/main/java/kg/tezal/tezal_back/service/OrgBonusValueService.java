package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrgBonusValue;
import kg.tezal.tezal_back.model.BonusValueModel;

import java.util.List;

public interface OrgBonusValueService {
    OrgBonusValue findById(Long id);

    List<OrgBonusValue> findAll();

    OrgBonusValue create(OrgBonusValue orgBonusValue);

    String deleteById(Long id);

    OrgBonusValue putById(Long id, OrgBonusValue orgBonusValue);

    BonusValueModel getBonusValueByOrgIdAndTypeId(Long orgId, Long typeId);
}
