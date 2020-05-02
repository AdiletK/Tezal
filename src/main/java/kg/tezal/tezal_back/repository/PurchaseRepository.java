package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Purchase;
import kg.tezal.tezal_back.model.PurchaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query("select new kg.tezal.tezal_back.model.PurchaseModel(purchase.id, purchase.count, purchase.summ, purchase.publisher, purchase.barCode, purchase.priceForOne, purchase.rawMaterial.id, purchase.rawMaterial.name, purchase.organization.id, purchase.organization.name,purchase.supplier.id, purchase.supplier.name, purchase.createDate) FROM Purchase purchase WHERE purchase.organization.id = :id  ORDER BY purchase.organization.id ASC")
    List<PurchaseModel> findAllByOrgId(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.PurchaseModel(purchase.id, purchase.count, purchase.summ, purchase.publisher, purchase.barCode, purchase.priceForOne, purchase.rawMaterial.id, purchase.rawMaterial.name, purchase.organization.id, purchase.organization.name,purchase.supplier.id, purchase.supplier.name, purchase.createDate) FROM Purchase purchase WHERE purchase.id = :id ")
    PurchaseModel getPurchaseById(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.PurchaseModel(purchase.id, purchase.count, purchase.summ, purchase.publisher, purchase.barCode, purchase.priceForOne, purchase.rawMaterial.id, purchase.rawMaterial.name, purchase.organization.id, purchase.organization.name,purchase.supplier.id, purchase.supplier.name, purchase.createDate) FROM Purchase purchase WHERE " +
            "purchase.organization.id = :id and (lower(purchase.rawMaterial.name) like %:search% or lower(purchase.rawMaterial.description) like %:search% or lower(purchase.supplier.name) like %:search%) ORDER BY purchase.id ASC")
    Page<PurchaseModel> findAllByOrgIdAndByNameOrDescription(@Param("id") Long id, String search, Pageable pageable);

}
