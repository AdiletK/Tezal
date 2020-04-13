package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.dao.BonusDao;
import kg.tezal.tezal_back.entity.OrgBonusValue;
import kg.tezal.tezal_back.model.BonusValueModel;
import kg.tezal.tezal_back.repository.OrgBonusValueRepository;
import kg.tezal.tezal_back.service.OrgBonusValueService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgBonusValueServiceImpl implements OrgBonusValueService {
    @Autowired
    private OrgBonusValueRepository orgBonusValueRepository;
    @Autowired
    private BonusDao bonusDao;

    public OrgBonusValue findById(Long id) {
        return orgBonusValueRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<OrgBonusValue> findAll() {
        return orgBonusValueRepository.findAll();
    }

    public OrgBonusValue create(OrgBonusValue orgBonusValue) {
        return orgBonusValueRepository.save(orgBonusValue);
    }

    public String deleteById(Long id) {
        orgBonusValueRepository.deleteById(id);
        return "OrgBonusValue number " + id + " has been deleted!";
    }

    public OrgBonusValue putById(Long id, OrgBonusValue orgBonusValue) {
        return orgBonusValueRepository.findById(id)
                .map(newOrgBonusValue -> {
                    newOrgBonusValue.setMax(orgBonusValue.getMax());
                    newOrgBonusValue.setMin(orgBonusValue.getMin());
                    newOrgBonusValue.setOrgBonus(orgBonusValue.getOrgBonus());
                    newOrgBonusValue.setValue(orgBonusValue.getValue());
                    return orgBonusValueRepository.save(newOrgBonusValue);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public BonusValueModel getBonusValueByOrgIdAndTypeId(Long orgId, Long typeId) {
        return bonusDao.getBonusAndValueByOrgIdAndTypeId(orgId, typeId);
    }


}
