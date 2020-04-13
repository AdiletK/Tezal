package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.BalanceRestController;
import kg.tezal.tezal_back.apicontroller.UserRestController;
import kg.tezal.tezal_back.dao.BalanceDao;
import kg.tezal.tezal_back.dao.BalanceHistoryDao;
import kg.tezal.tezal_back.entity.BalanceHistory;
import kg.tezal.tezal_back.model.BalanceHistoryLongModel;
import kg.tezal.tezal_back.model.BalanceHistoryModel;
import kg.tezal.tezal_back.model.HistoryModel;
import kg.tezal.tezal_back.repository.BalanceHistoryRepository;
import kg.tezal.tezal_back.service.BalanceHistoryService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceHistoryServiceImpl implements BalanceHistoryService {
    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;
    @Autowired
    private BalanceRestController balanceRestController;
    @Autowired
    private UserRestController userRestController;
    @Autowired
    private BalanceDao balanceDao;
    @Autowired
    private BalanceHistoryDao balanceHistoryDao;


    public BalanceHistory findById(Long id) {
        return balanceHistoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }
    public List<HistoryModel> findHistoryByClientId(Long clientId, String last, Long hid, Integer pageSize) {
        return balanceDao.getBalanceHistoryByClientId(clientId, last, hid, pageSize);
    }

    @Override
    public List<HistoryModel> findHistoryByClientAndOrgAndBonusTypeId(Long clientId, Long orgId, Long typeId) {
        return balanceDao.getBalanceHistoryByClientIdAndOrgIdAndBonusTypeId(clientId, orgId, typeId);
    }

    @Override
    public List<BalanceHistoryLongModel> getCashierOperationHistory(Long userId, String dateFrom, String dateTo) {
        return balanceHistoryDao.getCashierOperationHistory(userId, dateFrom, dateTo);
    }


    public List<BalanceHistory> findAll() {
        return balanceHistoryRepository.findAll();
    }

    public BalanceHistory create(BalanceHistoryModel balanceHistoryModel) {
        BalanceHistory balanceHistory = new BalanceHistory();
        balanceHistory.setAmount(balanceHistoryModel.getAmount());
        balanceHistory.setUser(userRestController.getUserById(balanceHistoryModel.getUserId()));
        balanceHistory.setBalance(balanceRestController.getBalanceById(
                balanceDao.getBalanceIdByClientIdAndOrgIdAndBonusTypeId(
                        balanceHistoryModel.getClientId(),balanceHistoryModel.getOrgId(),balanceHistoryModel.getTypeId()
                )));
        balanceHistory.setBill(balanceHistoryModel.getBill());
        balanceHistory.setInvoice_amount(balanceHistoryModel.getInvoiceAmount());
        balanceHistory.setOperationType(balanceHistoryModel.getOperationType());
        return balanceHistoryRepository.save(balanceHistory);
    }

    public String deleteById(Long id) {
        balanceHistoryRepository.deleteById(id);
        return "BalanceHistory number " + id + " has been deleted!";
    }

    public BalanceHistory putById(Long id, BalanceHistory balanceHistory) {
        return balanceHistoryRepository.findById(id)
                .map(newBalanceHistory -> {
                    newBalanceHistory.setAmount(balanceHistory.getAmount());
                    newBalanceHistory.setBalance(balanceHistory.getBalance());
                    newBalanceHistory.setOperationType(balanceHistory.getOperationType());
                    return balanceHistoryRepository.save(newBalanceHistory);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

}
