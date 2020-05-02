package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.OrganizationRestController;
import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.entity.Rate;
import kg.tezal.tezal_back.model.RateModel;
import kg.tezal.tezal_back.repository.RateRepository;
import kg.tezal.tezal_back.service.RateService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final OrganizationRestController organizationRestController;
    private final RawMaterialRestController materialRestController;

    public RateServiceImpl(RateRepository rateRepository, OrganizationRestController organizationRestController, RawMaterialRestController materialRestController) {
        this.rateRepository = rateRepository;
        this.organizationRestController = organizationRestController;
        this.materialRestController = materialRestController;
    }

    @Override
    public Rate findById(Long id) {
        return rateRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public RateModel getById(Long id) {
        return rateRepository.getRateById(id);
    }


    @Override
    public List<RateModel> findAllByOrgId(Long id) {
        return rateRepository.findAllByOrgId(id);
    }

    @Override
    public Page<RateModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable) {
        return rateRepository.findAllByOrgIdAndByNameOrDescription(id, search, pageable);
    }

    @Override
    public Rate create(RateModel rateModel) {
        Rate rate = new Rate();
        return getRate(rateModel, rate);
    }

    @Override
    public String deleteById(Long id) {
        rateRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public Rate putById(Long id, RateModel rateModel) {
        Rate rate = new Rate();
        rate.setId(id);
        return getRate(rateModel, rate);
    }

    @Override
    public boolean isEnoughOrderInStock(Long orgId, Long matId, Float count) {
        RateModel rateModel = rateRepository.getRateByOrgIdAndRawMaterial(orgId, matId);
        return rateModel.getQuantityInStock() >= count;
    }

    @Override
    public void decreaseAmountMaterial(Long orgId, Long matId, Float count) {
        RateModel rateModel = rateRepository.getRateByOrgIdAndRawMaterial(orgId, matId);
        rateModel.setQuantityInStock(rateModel.getQuantityInStock() - count);
        putById(rateModel.getId(), rateModel);
        rateRepository.flush();
    }

    @Override
    public void increaseAmountMaterial(Long orgId, Long matId, Float count) {
        RateModel rateModel = rateRepository.getRateByOrgIdAndRawMaterial(orgId, matId);
        System.out.println(rateModel.getId() + "-------------------" + rateModel.getQuantityInStock() + " " + count);
        rateModel.setQuantityInStock(rateModel.getQuantityInStock() + count);
        putById(rateModel.getId(), rateModel);
        rateRepository.flush();
    }

    private Rate getRate(RateModel rateModel, Rate rate) {
        rate.setQuantityInStock(rateModel.getQuantityInStock());
        rate.setOrganization(organizationRestController.getOrganizationById(rateModel.getOrganizationId()));
        rate.setRawMaterial(materialRestController.getRawMaterialById(rateModel.getRawMaterialId()));
        rate.setRetailPrice(rateModel.getRetailPrice());
        rate.setWholesalePrice(rateModel.getWholesalePrice());
        return rateRepository.save(rate);
    }


}
