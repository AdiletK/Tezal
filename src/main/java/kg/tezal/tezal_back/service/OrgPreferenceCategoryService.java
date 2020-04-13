package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.OrgPreferenceCategory;

import java.util.List;

public interface OrgPreferenceCategoryService {
    OrgPreferenceCategory findById(Long id);

    List<OrgPreferenceCategory> findAll();

    OrgPreferenceCategory create(OrgPreferenceCategory orgPreferenceCategory);

    String deleteById(Long id);

    OrgPreferenceCategory putById(Long id, OrgPreferenceCategory orgPreferenceCategory);
}
