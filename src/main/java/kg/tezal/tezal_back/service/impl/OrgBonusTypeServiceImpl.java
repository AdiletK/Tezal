package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.OrgBonusType;
import kg.tezal.tezal_back.model.OrgBonusTypeModel;
import kg.tezal.tezal_back.repository.OrgBonusTypeRepository;
import kg.tezal.tezal_back.service.OrgBonusTypeService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgBonusTypeServiceImpl implements OrgBonusTypeService {
    @Autowired
    private OrgBonusTypeRepository orgBonusTypeRepository;

    public OrgBonusType findById(Long id) {
        return orgBonusTypeRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<OrgBonusType> findAll() {
        return orgBonusTypeRepository.findAll();
    }

    public List<OrgBonusTypeModel> findAllOrgBonusTypeDto() {
        return orgBonusTypeRepository.findAllOrgBonusTypeDto();
    }

    public OrgBonusType create(OrgBonusType orgBonusType) {
        return orgBonusTypeRepository.save(orgBonusType);
    }

    public String deleteById(Long id) {
        orgBonusTypeRepository.deleteById(id);
        return "OrgBonusType number " + id + " has been deleted!";
    }

    public OrgBonusType putById(Long id, OrgBonusType orgBonusType) {
        return orgBonusTypeRepository.findById(id)
                .map(newOrgBonusType -> {
                    newOrgBonusType.setName(orgBonusType.getName());
                    newOrgBonusType.setValueType(orgBonusType.getValueType());
                    return orgBonusTypeRepository.save(newOrgBonusType);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }


}
