package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Balance;
import kg.tezal.tezal_back.model.BalanceModel;
import kg.tezal.tezal_back.model.HistoryModel;

import java.util.List;

public interface BalanceService {
    Balance findById(Long id);

    List<Balance> findAll();

    Balance create(Balance balance);

    String deleteById(Long id);

    Balance putById(Long id, Balance balance);

    List<HistoryModel> findBalanceByClientAndOrgAndBonusTypeId(Long clientId, Long orgId, Long typeId);

    List<BalanceModel> getClientBalanceByClientCodeOrgId(String code, Long orgId);

    boolean updateBalance(Double amount,Long clientId, Long orgBonusId);
}
