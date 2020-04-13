package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.OrgCategory;
import kg.tezal.tezal_back.entity.OrgCategoryTag;
//<<<<<<< Updated upstream
import kg.tezal.tezal_back.model.OrgCategoryImageModel;
//=======
//>>>>>>> Stashed changes
import kg.tezal.tezal_back.model.OrgCategoryModel;
import kg.tezal.tezal_back.repository.OrgCategoryRepository;
import kg.tezal.tezal_back.service.OrgCategoryService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import kg.tezal.tezal_back.utils.UtilBase64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrgCategoryServiceImpl implements OrgCategoryService {
    @Autowired
    private OrgCategoryRepository orgCategoryRepository;

    public OrgCategory findById(Long id) {
        return orgCategoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<OrgCategory> findAll() {
        return orgCategoryRepository.findAll();
    }

    public Page<OrgCategoryModel> findAll(Pageable pageable) {
        return orgCategoryRepository.getOrgCategoryListByPagination(pageable);
    }

//    public OrgCategory create(OrgCategory orgCategory) {
//        return orgCategoryRepository.save(orgCategory);
//    }


    public OrgCategory create(OrgCategoryImageModel orgCategoryModel) {
        OrgCategory orgCategory = new OrgCategory();
        if(!orgCategoryModel.getImage().isEmpty() && orgCategoryModel.getImage().getContentType().contains("image"))
            orgCategory.setImage(UtilBase64Image.encoder(orgCategoryModel.getImage()));
        orgCategory.setName(orgCategoryModel.getName());
        orgCategory.setDescription(orgCategoryModel.getDescription());
        return orgCategoryRepository.save(orgCategory);
    }

    public String deleteById(Long id) {
        orgCategoryRepository.deleteById(id);
        return "OrgCategory number " + id + " has been deleted!";
    }

    public OrgCategory putById(Long id, OrgCategoryImageModel orgCategoryModel) {
        return orgCategoryRepository.findById(id)
                .map(newOrgCategory -> {
                    newOrgCategory.setDescription(orgCategoryModel.getDescription());
                    newOrgCategory.setName(orgCategoryModel.getName());
//                    newOrgCategory.setTags(organizationModel.getTags());
                    if(!orgCategoryModel.getImage().isEmpty() && orgCategoryModel.getImage().getContentType().contains("image"))
                        newOrgCategory.setImage(UtilBase64Image.encoder(orgCategoryModel.getImage()));
                    return orgCategoryRepository.save(newOrgCategory);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }
//    public OrgCategory putById(Long id, OrgCategory orgCategory) {
//        return orgCategoryRepository.findById(id)
//                .map(newOrgCategory -> {
//                    newOrgCategory.setDescription(orgCategory.getDescription());
//                    newOrgCategory.setName(orgCategory.getName());
//                    newOrgCategory.setImageUrl(orgCategory.getImageUrl());
//                    newOrgCategory.setName(orgCategory.getName());
//                    newOrgCategory.setTags(orgCategory.getTags());
//                    return orgCategoryRepository.save(newOrgCategory);
//                })
//                .orElseThrow(() ->
//                        new RecordNotFoundException("Record not found with id:" + id));
//    }

    public OrgCategoryModel findOrgCategoryById(Long id) {
        return orgCategoryRepository.getOrgCategoryById(id);
    }

    public List<OrgCategoryModel> listAll() {
        return orgCategoryRepository.getOrgCategoryList();
    }

    @Override
    public void addTag(Long catId, OrgCategoryTag tag) {
        OrgCategory orgCategory = findById(catId);
        Set<OrgCategoryTag> tags = orgCategory.getTags();
        tags.add(tag);
        orgCategory.setTags(tags);
        orgCategoryRepository.save(orgCategory);
    }


}
