package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.RawMaterial;
import kg.tezal.tezal_back.model.RawMaterialModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    @Query("select new kg.tezal.tezal_back.model.RawMaterialModel(material.id, material.name,material.unit.id, material.description, material.volume, material.amountInBlock, material.materialCategory.id, material.materialCategory.name, material.image) FROM RawMaterial material where lower(material.name) like %?1% or lower(material.description) like %?1% ORDER BY material.name ASC")
    Page<RawMaterialModel> findAllByNameOrDescription(String search, Pageable pageable);

    @Query("select new kg.tezal.tezal_back.model.RawMaterialModel(material.id, material.name,material.unit.id, material.description, material.volume, material.amountInBlock, material.materialCategory.id, material.materialCategory.name, material.image) FROM RawMaterial material ORDER BY material.name ASC")
    Page<RawMaterialModel> findAllByNameOrDescription(Pageable pageable);


    @Query("select new kg.tezal.tezal_back.model.RawMaterialModel(material.id, material.name,material.unit.id, material.description, material.volume, material.amountInBlock, material.materialCategory.id, material.materialCategory.name, material.image) FROM RawMaterial material")
    List<RawMaterialModel> getAllMaterials();

    @Query("select new kg.tezal.tezal_back.model.RawMaterialShortModel(material.id, material.name, material.volume, material.image) FROM RawMaterial material")
    List<RawMaterialShortModel> getAllMaterialsName();

    @Query("select new kg.tezal.tezal_back.model.RawMaterialModel(material.id, material.name,material.unit.id, material.description, material.volume, material.amountInBlock, material.materialCategory.id, material.materialCategory.name, material.image) FROM RawMaterial material where material.id = :id")
    RawMaterialModel getRawMaterialById(Long id);
}
