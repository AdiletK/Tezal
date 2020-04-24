package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.OrganizationRestController;
import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.apicontroller.SupplierRestController;
import kg.tezal.tezal_back.entity.Purchase;
import kg.tezal.tezal_back.model.PurchaseModel;
import kg.tezal.tezal_back.repository.PurchaseRepository;
import kg.tezal.tezal_back.service.PurchaseService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository rateRepository;
    private final OrganizationRestController organizationRestController;
    private final RawMaterialRestController materialRestController;
    private final SupplierRestController supplierRestController;

    public PurchaseServiceImpl(PurchaseRepository rateRepository, OrganizationRestController organizationRestController, RawMaterialRestController materialRestController, SupplierRestController supplierRestController) {
        this.rateRepository = rateRepository;
        this.organizationRestController = organizationRestController;
        this.materialRestController = materialRestController;
        this.supplierRestController = supplierRestController;
    }

    @Override
    public Purchase findById(Long id) {
        return rateRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public PurchaseModel getById(Long id) {
        return rateRepository.getPurchaseById(id);
    }


    @Override
    public List<PurchaseModel> findAllByOrgId(Long id) {
        return rateRepository.findAllByOrgId(id);
    }

    @Override
    public Page<PurchaseModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable) {
        return rateRepository.findAllByOrgIdAndByNameOrDescription(id, search, pageable);
    }

    @Override
    public Purchase create(PurchaseModel purchaseModel) {
        Purchase rate = new Purchase();
        return getPurchase(purchaseModel, rate);
    }

    @Override
    public String deleteById(Long id) {
        rateRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public Purchase putById(Long id, PurchaseModel purchaseModel) {
        Purchase rate = new Purchase();
        rate.setId(id);
        return getPurchase(purchaseModel, rate);
    }

    private Purchase getPurchase(PurchaseModel purchaseModel, Purchase rate) {
        rate.setCount(purchaseModel.getCount());
        rate.setOrganization(organizationRestController.getOrganizationById(purchaseModel.getOrganizationId()));
        rate.setRawMaterial(materialRestController.getRawMaterialById(purchaseModel.getRawMaterialId()));
        rate.setSupplier(supplierRestController.findById(purchaseModel.getSupplierId()));
        rate.setBarCode(purchaseModel.getBarCode());
        rate.setPriceForOne(purchaseModel.getPriceForOne());
        rate.setSumm(purchaseModel.getSumm());
        rate.setPublisher(purchaseModel.getPublisher());
        return rateRepository.save(rate);
    }


}
