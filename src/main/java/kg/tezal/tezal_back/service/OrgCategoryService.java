package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrgCategory;
import kg.tezal.tezal_back.entity.OrgCategoryTag;
//<<<<<<< Updated upstream
import kg.tezal.tezal_back.model.OrgCategoryImageModel;
//=======
//>>>>>>> Stashed changes
import kg.tezal.tezal_back.model.OrgCategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrgCategoryService {
    OrgCategory findById(Long id);

    List<OrgCategory> findAll();

    Page<OrgCategoryModel> findAll(Pageable pageable);

    OrgCategory create(OrgCategoryImageModel orgCategoryModel);

    String deleteById(Long id);

    OrgCategory putById(Long id, OrgCategoryImageModel orgCategoryModel);

    OrgCategoryModel findOrgCategoryById(Long id);

    List<OrgCategoryModel> listAll();

    void addTag(Long catId, OrgCategoryTag tag);
}
