package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.enums.OperationType;
import kg.tezal.tezal_back.model.BalanceConfirmModel;
import kg.tezal.tezal_back.model.BonusValueModel;
import kg.tezal.tezal_back.model.HistoryModel;

import java.util.List;

public interface CashierService {
    BonusValueModel getBonusValueByOrgIdAndTypeId(Long orgId, Long typeId);

    boolean updateBalance(Double point, Long clientId, Long bonusId);

    void insertBalanceHistory(Long orgId, Long userId, BalanceConfirmModel balanceConfirm, BonusValueModel bonusValueModel, OperationType operationType);

    List<HistoryModel> findBalanceByClientAndOrgAndBonusTypeId(Long clientId, Long orgId, Long typeId);

    void addBonusToBalance(Long orgId, Long userId, BalanceConfirmModel balanceConfirm, Integer bonusValue);
}
