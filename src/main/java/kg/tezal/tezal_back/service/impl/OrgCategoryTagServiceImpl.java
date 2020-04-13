package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.dao.OrgCategoryTagDao;
import kg.tezal.tezal_back.entity.OrgCategoryTag;
import kg.tezal.tezal_back.model.OrgCategoryTagModel;
import kg.tezal.tezal_back.repository.OrgCategoryTagRepository;
import kg.tezal.tezal_back.service.OrgCategoryTagService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgCategoryTagServiceImpl implements OrgCategoryTagService {
    @Autowired
    private OrgCategoryTagDao orgCategoryTagDao;
    @Autowired
    private OrgCategoryTagRepository orgCategoryTagRepository;

    public OrgCategoryTag findById(Long id) {
        return orgCategoryTagRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }
    public OrgCategoryTagModel getTagById(Long id) {
        return orgCategoryTagRepository.getTagById(id);
    }

    public List<OrgCategoryTag> findAll() {
        return orgCategoryTagRepository.findAll();
    }

    public OrgCategoryTag create(OrgCategoryTagModel tagModel) {
        OrgCategoryTag orgCategoryTag = new OrgCategoryTag();
        orgCategoryTag.setName(tagModel.getName());

        return orgCategoryTagRepository.save(orgCategoryTag);
    }
//    public OrgCategoryTag create(OrgCategoryTag orgCategoryTag) {
//        return orgCategoryTagRepository.save(orgCategoryTag);
//    }

    public String deleteById(Long id) {
        orgCategoryTagRepository.deleteById(id);
        return "OrgCategoryTag number " + id + " has been deleted!";
    }

    @Override
    public OrgCategoryTagModel putById(Long id, OrgCategoryTagModel orgCategoryTag) {
        OrgCategoryTag categoryTag = new OrgCategoryTag();
        categoryTag.setId(id);
        categoryTag.setName(orgCategoryTag.getName());
        orgCategoryTagRepository.save(categoryTag);
        return orgCategoryTag;
    }

    public OrgCategoryTag putById(Long id, OrgCategoryTag orgCategoryTag) {
        return orgCategoryTagRepository.findById(id)
                .map(newOrgCategoryTag -> {
                    newOrgCategoryTag.setName(orgCategoryTag.getName());
                    newOrgCategoryTag.setOrgCategories(orgCategoryTag.getOrgCategories());
                    return orgCategoryTagRepository.save(newOrgCategoryTag);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public List<OrgCategoryTagModel> getTagsByCategoryId(Long categoryId) {
        return orgCategoryTagDao.getTagsByOrgCategoryId(categoryId);
    }


}
