package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.MaterialCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialCategoryService {
    MaterialCategory findById(Long id);

    MaterialCategory create(MaterialCategory category);

    String deleteById(Long id);

    MaterialCategory putById(Long id, MaterialCategory category);

    Page<MaterialCategory> findAll(Pageable pageable);

    List<MaterialCategory> findAll();
}
