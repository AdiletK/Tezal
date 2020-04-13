package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.PreferenceCategory;

import java.util.List;

public interface PreferenceCategoryService {
    PreferenceCategory findById(Long id);

    List<PreferenceCategory> findAll();

    PreferenceCategory create(PreferenceCategory preferenceCategory);

    String deleteById(Long id);

    PreferenceCategory putById(Long id, PreferenceCategory preferenceCategory);
}
