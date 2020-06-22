package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Rate;
import kg.tezal.tezal_back.model.RateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RateService {
    Rate findById(Long id);

    RateModel getById(Long id);

    List<RateModel> findAllByOrgId(Long id);

    List<RateModel> findAllByOrgIdCatId(Long id, Long catId);

    Page<RateModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Rate create(RateModel rateModel);

    String deleteById(Long id);

    Rate putById(Long id, RateModel rateModel);

    boolean isEnoughOrderInStock(Long orgId, Long matId, Float count);

    void decreaseAmountMaterial(Long orgId, Long matId, Float count);

    void increaseAmountMaterial(Long orgId, Long matId, Float count);
}