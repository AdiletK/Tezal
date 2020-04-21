package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.MaterialCategory;
import kg.tezal.tezal_back.repository.MaterialCategoryRepository;
import kg.tezal.tezal_back.service.MaterialCategoryService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialCategoryServiceImpl implements MaterialCategoryService {
    private final MaterialCategoryRepository materialCategoryRepository;

    public MaterialCategoryServiceImpl(MaterialCategoryRepository materialCategoryRepository) {
        this.materialCategoryRepository = materialCategoryRepository;
    }

    @Override
    public MaterialCategory findById(Long id) {
        return materialCategoryRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public MaterialCategory create(MaterialCategory unit) {
        return materialCategoryRepository.save(unit);
    }

    @Override
    public String deleteById(Long id) {
        materialCategoryRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public MaterialCategory putById(Long id, MaterialCategory unit) {
        return materialCategoryRepository.findById(id)
                .map(newUnit -> {
                    newUnit.setName(unit.getName());
                    return materialCategoryRepository.save(newUnit);
                })
                .orElseThrow(() ->
            new RecordNotFoundException("Record not found with id:" + id));

    }

    @Override
    public Page<MaterialCategory> findAll(Pageable pageable) {
        return materialCategoryRepository.findAll(pageable);
    }

    @Override
    public List<MaterialCategory> findAll() {
        return materialCategoryRepository.findAll();
    }
}
