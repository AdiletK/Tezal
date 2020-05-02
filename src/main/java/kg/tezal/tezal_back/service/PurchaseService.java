package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Purchase;
import kg.tezal.tezal_back.model.PurchaseModel;
import kg.tezal.tezal_back.model.PurchaseShortModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseService {
    Purchase findById(Long id);

    PurchaseModel getById(Long id);

    List<PurchaseModel> findAllByOrgId(Long id);

    List<PurchaseShortModel> findAllByOrgIdWithDate(Long id, String dateFrom, String dateTo);

    Page<PurchaseModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Purchase create(PurchaseModel purchaseModel);

    String deleteById(Long id);

    Purchase putById(Long id, PurchaseModel purchaseModel);



}