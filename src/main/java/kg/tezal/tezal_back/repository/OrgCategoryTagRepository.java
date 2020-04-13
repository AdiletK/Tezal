package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrgCategoryTag;
import kg.tezal.tezal_back.model.OrgCategoryTagModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrgCategoryTagRepository extends JpaRepository<OrgCategoryTag, Long> {
    @Query("select new kg.tezal.tezal_back.model.OrgCategoryTagModel(tag.id, tag.name) from   OrgCategoryTag  tag where tag.id =:id")
    OrgCategoryTagModel getTagById(Long id);
}
