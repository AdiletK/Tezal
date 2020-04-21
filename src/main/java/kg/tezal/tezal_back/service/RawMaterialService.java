package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.RawMaterial;
import kg.tezal.tezal_back.model.RawMaterialModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RawMaterialService {

    Page<RawMaterialModel> findAllByNameOrDescription(String search, Pageable pageable);

    RawMaterial create(RawMaterialModel materialModel);

    String deleteById(Long id);

    RawMaterial putById(Long id, RawMaterialModel materialModel);

    RawMaterialModel getRawMaterialById(Long id);

    RawMaterial findById(Long id);
}
