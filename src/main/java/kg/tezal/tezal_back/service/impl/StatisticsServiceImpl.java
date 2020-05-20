package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.dao.StatisticsDao;
import kg.tezal.tezal_back.model.StatisticsModel;
import kg.tezal.tezal_back.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsDao statisticsDao;

    public StatisticsServiceImpl(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    @Override
    public List<StatisticsModel> getSoldProductsByOrgId(Long orgId) {
        return statisticsDao.getSoldProductsByOrgId(orgId);
    }

    @Override
    public List<StatisticsModel> getOrderStatisticByOrgId(Long orgId, String dateFrom, String dateTo) {
        return statisticsDao.getOrderStatisticByOrgId(orgId, dateFrom, dateTo);
    }
}
