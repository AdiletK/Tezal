package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.RawMaterial;
import kg.tezal.tezal_back.model.RawMaterialModel;
import kg.tezal.tezal_back.model.RawMaterialModelImage;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface RawMaterialService {

    Page<RawMaterialModel> findAllByNameOrDescription(String search, Pageable pageable);

    Page<RawMaterialModel> findAllByNameOrDescription(Pageable pageable);

    RawMaterial create(RawMaterialModelImage materialModel);

    String deleteById(Long id);

    RawMaterial putById(Long id, RawMaterialModelImage materialModel);

    RawMaterialModel getRawMaterialById(Long id);

    List<RawMaterialModel> getRawMaterials();

    List<RawMaterialShortModel> getRawMaterialsName();

    RawMaterial findById(Long id);
}
