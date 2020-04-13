package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.BalanceHistory;
import kg.tezal.tezal_back.model.BalanceHistoryLongModel;
import kg.tezal.tezal_back.model.BalanceHistoryModel;
import kg.tezal.tezal_back.model.HistoryModel;

import java.util.List;

public interface BalanceHistoryService {
    BalanceHistory findById(Long id);

    List<BalanceHistory> findAll();

//    BalanceHistory create(BalanceHistory BalanceHistory);
    BalanceHistory create(BalanceHistoryModel balanceHistoryModel);

    String deleteById(Long id);

    BalanceHistory putById(Long id, BalanceHistory BalanceHistory);

    List<HistoryModel> findHistoryByClientId(Long clientId, String last, Long historyId, Integer pageSize);

    List<HistoryModel> findHistoryByClientAndOrgAndBonusTypeId(Long clientId, Long orgId, Long typeId);

    List<BalanceHistoryLongModel> getCashierOperationHistory(Long userId, String dateFrom, String dateTo);

}
