package kg.tezal.tezal_back.service.impl;


import kg.tezal.tezal_back.dao.BonusDao;
import kg.tezal.tezal_back.entity.OrgBonus;
import kg.tezal.tezal_back.model.BonusModel;
import kg.tezal.tezal_back.model.BonusShortModel;
import kg.tezal.tezal_back.repository.OrgBonusRepository;
import kg.tezal.tezal_back.service.OrgBonusService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrgBonusServiceImpl implements OrgBonusService {

    @Autowired
    private OrgBonusRepository orgBonusRepository;

    @Autowired
    private BonusDao bonusDao;

    public OrgBonus findById(Long id) {
        return orgBonusRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<OrgBonus> findAll() {
        return orgBonusRepository.findAll();
    }

    public List<BonusModel> findAllOrgBonusList() {
        return orgBonusRepository.findAllOrgBonusList();
    }

    public List<BonusModel> findAllOrgBonusListByOrgId(Long id) {
        return orgBonusRepository.findAllOrgBonusListByOrgId(id);
    }

    public List<BonusShortModel> getAllOrgBonusListByOrgId(Long orgId, Integer lastValidity, Long lastId, Integer limit) {
        return bonusDao.getBonusByOrgId(orgId,lastValidity,lastId,limit);
    }

    public OrgBonus create(OrgBonus orgBonus) {
        return orgBonusRepository.save(orgBonus);
    }

    public String deleteById(Long id) {
        orgBonusRepository.deleteById(id);
        return "OrgBonus number " + id + " has been deleted!";
    }

    public List<BonusModel> findByCriteria(Long orgId, Long bonusTypeId, Date fromDate, Date toDate, Boolean status) {
        return orgBonusRepository.findAll(new Specification<BonusModel>() {
            @Override
            public Predicate toPredicate(Root<BonusModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (orgId != null)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("organization"), orgId)));
                if (bonusTypeId != null)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgBonusType"), bonusTypeId)));
                if (fromDate != null & toDate != null)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("createdDate"), fromDate, toDate)));
                if (status != null)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    public OrgBonus putById(Long id, OrgBonus orgBonus) {
        return orgBonusRepository.findById(id)
                .map(newOrgBonus -> {
                    newOrgBonus.setOrganization(orgBonus.getOrganization());
                    newOrgBonus.setValidFrom(orgBonus.getValidFrom());
                    newOrgBonus.setStatus(orgBonus.getStatus());
                    newOrgBonus.setOrgBonusType(orgBonus.getOrgBonusType());
                    newOrgBonus.setValidTo(orgBonus.getValidTo());
                    return orgBonusRepository.save(newOrgBonus);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public void changeStatus(Long id) {
        OrgBonus orgBonus = orgBonusRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
        if (orgBonus.getStatus()==null || !orgBonus.getStatus()){
            orgBonus.setStatus(true);
        } else
            orgBonus.setStatus(false);
        orgBonusRepository.save(orgBonus);
    }

}
