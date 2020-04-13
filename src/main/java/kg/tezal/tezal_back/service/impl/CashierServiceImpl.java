package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.enums.OperationType;
import kg.tezal.tezal_back.model.BalanceConfirmModel;
import kg.tezal.tezal_back.model.BalanceHistoryModel;
import kg.tezal.tezal_back.model.BonusValueModel;
import kg.tezal.tezal_back.model.HistoryModel;
import kg.tezal.tezal_back.service.BalanceHistoryService;
import kg.tezal.tezal_back.service.BalanceService;
import kg.tezal.tezal_back.service.CashierService;
import kg.tezal.tezal_back.service.OrgBonusValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashierServiceImpl implements CashierService {
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private OrgBonusValueService orgBonusValueService;
    @Autowired
    private BalanceHistoryService balanceHistoryService;

    @Override
    public BonusValueModel getBonusValueByOrgIdAndTypeId(Long orgId, Long typeId) {
        return orgBonusValueService.getBonusValueByOrgIdAndTypeId(orgId, typeId);
    }

    @Override
    public boolean updateBalance(Double point, Long clientId, Long bonusId) {
        return balanceService.updateBalance(point , clientId, bonusId);
    }

    @Override
    public void insertBalanceHistory(Long orgId, Long userId,
                                     BalanceConfirmModel balanceConfirm,
                                     BonusValueModel bonusValueModel, OperationType operationType) {
        BalanceHistoryModel balanceHistoryModel = new BalanceHistoryModel();
        balanceHistoryModel.setOrgId(orgId);
        balanceHistoryModel.setUserId(userId);
        balanceHistoryModel.setBonusId(bonusValueModel.getBonusId());
        balanceHistoryModel.setClientId(balanceConfirm.getClientId());
        balanceHistoryModel.setAmount(balanceConfirm.getPoint());
        balanceHistoryModel.setBill(balanceConfirm.getNumberCheck());
        balanceHistoryModel.setOperationType(operationType);
        balanceHistoryModel.setInvoiceAmount(balanceConfirm.getInvoiceAmount());
        balanceHistoryService.create(balanceHistoryModel);
    }

    @Override
    public List<HistoryModel> findBalanceByClientAndOrgAndBonusTypeId(Long clientId, Long orgId, Long typeId) {
        return balanceService.findBalanceByClientAndOrgAndBonusTypeId(clientId, orgId, typeId);
    }

    @Override
    public void addBonusToBalance(Long orgId, Long userId, BalanceConfirmModel balanceConfirm, Integer bonusValue) {
        BonusValueModel bonusValueModel = getBonusValueByOrgIdAndTypeId(orgId, 3l);
        double cashAmount = - (balanceConfirm.getInvoiceAmount() - balanceConfirm.getPoint()) * bonusValue / 100;
        balanceConfirm.setPoint(-cashAmount);
        updateBalance(cashAmount , balanceConfirm.getClientId(), bonusValueModel.getBonusId());
        insertBalanceHistory(orgId, userId, balanceConfirm, bonusValueModel, OperationType.DEBIT);
    }
}
