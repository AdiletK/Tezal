package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.model.StatisticsModel;

import java.util.List;

public interface StatisticsService {
    List<StatisticsModel> getSoldProductsByOrgId(Long orgId);

    List<StatisticsModel> getOrderStatisticByOrgId(Long orgId, String dateFrom, String dateTo);

    List<StatisticsModel> getUserStatisticByOrgId(Long orgId, String dateFrom, String dateTo);
}
