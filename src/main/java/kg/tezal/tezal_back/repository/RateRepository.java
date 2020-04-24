package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Rate;
import kg.tezal.tezal_back.model.RateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query("select new kg.tezal.tezal_back.model.RateModel(rate.id, rate.wholesalePrice, rate.retailPrice, rate.quantityInStock, rate.rawMaterial.id, rate.rawMaterial.name, rate.organization.id, rate.organization.name) FROM Rate rate WHERE rate.organization.id = :id  ORDER BY rate.organization.id ASC")
    List<RateModel> findAllByOrgId(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.RateModel(rate.id, rate.wholesalePrice, rate.retailPrice, rate.quantityInStock, rate.rawMaterial.id, rate.rawMaterial.name, rate.organization.id, rate.organization.name) FROM Rate rate WHERE rate.id = :id ")
    RateModel getRateById(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.RateModel(rate.id, rate.wholesalePrice, rate.retailPrice, rate.quantityInStock, rate.rawMaterial.id, rate.rawMaterial.name, rate.organization.id, rate.organization.name) FROM Rate rate WHERE rate.organization.id = :id and (lower(rate.rawMaterial.name) like %:search% or lower(rate.rawMaterial.description) like %:search%) ORDER BY rate.id ASC")
    Page<RateModel> findAllByOrgIdAndByNameOrDescription(@Param("id") Long id, String search, Pageable pageable);
}
