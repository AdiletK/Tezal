package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.MaterialCategoryRestController;
import kg.tezal.tezal_back.apicontroller.UnitRestController;
import kg.tezal.tezal_back.entity.RawMaterial;
import kg.tezal.tezal_back.model.RawMaterialModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.repository.RawMaterialRepository;
import kg.tezal.tezal_back.service.RawMaterialService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {
    private final RawMaterialRepository materialRepository;
    private final UnitRestController unitRestController;
    private final MaterialCategoryRestController materialCategoryRestController;

    public RawMaterialServiceImpl(RawMaterialRepository materialRepository, UnitRestController unitRestController, MaterialCategoryRestController materialCategoryRestController) {
        this.materialRepository = materialRepository;
        this.unitRestController = unitRestController;
        this.materialCategoryRestController = materialCategoryRestController;
    }

    @Override
    public Page<RawMaterialModel> findAllByNameOrDescription(String search, Pageable pageable) {
        return materialRepository.findAllByNameOrDescription(search, pageable);
    }

    @Override
    public RawMaterial create(RawMaterialModel materialModel) {
        RawMaterial rawMaterial = new RawMaterial();
        return getRawMaterial(materialModel, rawMaterial);
    }


    @Override
    public String deleteById(Long id) {
        materialRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public RawMaterial putById(Long id, RawMaterialModel materialModel) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(id);
        return getRawMaterial(materialModel, rawMaterial);
    }

    @Override
    public RawMaterialModel getRawMaterialById(Long id) {
        return materialRepository.getRawMaterialById(id);
    }

    @Override
    public List<RawMaterialModel> getRawMaterials() {
        return materialRepository.getAllMaterials();
    }

    @Override
    public List<RawMaterialShortModel> getRawMaterialsName() {
        return materialRepository.getAllMaterialsName();
    }

    @Override
    public RawMaterial findById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found with id:" + id));
    }

    private RawMaterial getRawMaterial(RawMaterialModel materialModel, RawMaterial rawMaterial) {
        rawMaterial.setName(materialModel.getName());
        rawMaterial.setDescription(materialModel.getDescription());
        rawMaterial.setUnit(unitRestController.getUnitById(materialModel.getUnitId()));
        rawMaterial.setVolume(materialModel.getVolume());
        rawMaterial.setAmountInBlock(materialModel.getAmountInBlock());
        rawMaterial.setMaterialCategory(materialCategoryRestController.getMaterialCategoryById(materialModel.getMaterialCategoryId()));
        return materialRepository.save(rawMaterial);
    }
}
