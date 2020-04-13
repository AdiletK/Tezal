package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrgBonus;
import kg.tezal.tezal_back.model.BonusModel;
import kg.tezal.tezal_back.model.BonusShortModel;

import java.util.Date;
import java.util.List;

public interface OrgBonusService {
    OrgBonus findById(Long id);

    List<OrgBonus> findAll();

    OrgBonus create(OrgBonus orgBonus);

    String deleteById(Long id);

    void changeStatus(Long id);

    OrgBonus putById(Long id, OrgBonus orgBonus);

    List<BonusModel> findAllOrgBonusList();

    List<BonusModel> findAllOrgBonusListByOrgId(Long id);

    List<BonusShortModel> getAllOrgBonusListByOrgId(Long orgId, Integer lastValidity, Long lastId, Integer limit);

    List<BonusModel> findByCriteria(Long orgId, Long bonusTypeId, Date validFrom, Date validTo, Boolean status);
}
