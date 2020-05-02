package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.OrganizationRestController;
import kg.tezal.tezal_back.apicontroller.RateRestController;
import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.apicontroller.SupplierRestController;
import kg.tezal.tezal_back.dao.ReportDao;
import kg.tezal.tezal_back.entity.Purchase;
import kg.tezal.tezal_back.model.PurchaseModel;
import kg.tezal.tezal_back.model.PurchaseShortModel;
import kg.tezal.tezal_back.repository.PurchaseRepository;
import kg.tezal.tezal_back.service.PurchaseService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final OrganizationRestController organizationRestController;
    private final RawMaterialRestController materialRestController;
    private final SupplierRestController supplierRestController;
    private final RateRestController rateRestController;
    @Autowired
    ReportDao reportDao;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, OrganizationRestController organizationRestController, RawMaterialRestController materialRestController, SupplierRestController supplierRestController, RateRestController rateRestController) {
        this.purchaseRepository = purchaseRepository;
        this.organizationRestController = organizationRestController;
        this.materialRestController = materialRestController;
        this.supplierRestController = supplierRestController;
        this.rateRestController = rateRestController;
    }

    @Override
    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public PurchaseModel getById(Long id) {
        return purchaseRepository.getPurchaseById(id);
    }


    @Override
    public List<PurchaseModel> findAllByOrgId(Long id) {
        return purchaseRepository.findAllByOrgId(id);
    }

    @Override
    public List<PurchaseShortModel> findAllByOrgIdWithDate(Long id, String dateFrom, String dateTo) {
        return reportDao.getPurchaseByOrgId(id, dateFrom, dateTo);
    }

    @Override
    public Page<PurchaseModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable) {
        return purchaseRepository.findAllByOrgIdAndByNameOrDescription(id, search, pageable);
    }

    @Override
    public Purchase create(PurchaseModel purchaseModel) {
        Purchase purchase = new Purchase();
        rateRestController.increaseAmountMaterial(purchaseModel.getOrganizationId(), purchaseModel.getRawMaterialId(), (float) purchaseModel.getCount());
        return getPurchase(purchaseModel, purchase);
    }

    @Override
    public String deleteById(Long id) {
        purchaseRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public Purchase putById(Long id, PurchaseModel purchaseModel) {
        Purchase purchase = purchaseRepository.findById(id).get();
        rateRestController.decreaseAmountMaterial(purchase.getOrganization().getId(), purchase.getRawMaterial().getId(), (float) purchase.getCount());
        rateRestController.increaseAmountMaterial(purchaseModel.getOrganizationId(), purchaseModel.getRawMaterialId(), (float) purchaseModel.getCount());
        return getPurchase(purchaseModel, purchase);
    }

    private Purchase getPurchase(PurchaseModel purchaseModel, Purchase purchase) {
        purchase.setCount(purchaseModel.getCount());
        purchase.setOrganization(organizationRestController.getOrganizationById(purchaseModel.getOrganizationId()));
        purchase.setRawMaterial(materialRestController.getRawMaterialById(purchaseModel.getRawMaterialId()));
        purchase.setSupplier(supplierRestController.findById(purchaseModel.getSupplierId()));
        purchase.setBarCode(purchaseModel.getBarCode());
        purchase.setPriceForOne(purchaseModel.getPriceForOne());
        purchase.setSumm(purchaseModel.getSumm());
        purchase.setPublisher(purchaseModel.getPublisher());
        return purchaseRepository.save(purchase);
    }


}
