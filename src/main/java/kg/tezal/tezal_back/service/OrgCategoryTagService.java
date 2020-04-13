package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrgCategoryTag;
import kg.tezal.tezal_back.model.OrgCategoryTagModel;

import java.util.List;

public interface OrgCategoryTagService {
    OrgCategoryTag findById(Long id);
    OrgCategoryTagModel getTagById(Long id);

    List<OrgCategoryTag> findAll();

//    OrgCategoryTag create(OrgCategoryTag orgCategoryTag);
    OrgCategoryTag create(OrgCategoryTagModel tagModel);

    String deleteById(Long id);

//    OrgCategoryTag putById(Long id, OrgCategoryTag orgCategoryTag);
    OrgCategoryTagModel putById(Long id, OrgCategoryTagModel orgCategoryTag);


    List<OrgCategoryTagModel> getTagsByCategoryId(Long categoryId);
}
