package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.model.StatisticsModel;
import kg.tezal.tezal_back.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/statistic")
public class StatisticsRestController {

    private final StatisticsService statisticsService;

    public StatisticsRestController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/sold/{orgId}")
    public List<StatisticsModel> getSoldProductsByOrgId(@PathVariable Long orgId) {
        return statisticsService.getSoldProductsByOrgId(orgId);
    }
    @GetMapping("/orders/{orgId}")
    public List<StatisticsModel> getOrdersByOrgId(@PathVariable Long orgId, @RequestParam("dateFrom")String dateFrom,
                                                 @RequestParam("dateTo")String dateTo) {
        return statisticsService.getOrderStatisticByOrgId(orgId, dateFrom, dateTo);
    }

    @GetMapping("/users/{orgId}")
    public List<StatisticsModel> getUsersByOrgId(@PathVariable Long orgId, @RequestParam("dateFrom")String dateFrom,
                                                  @RequestParam("dateTo")String dateTo) {
        return statisticsService.getUserStatisticByOrgId(orgId, dateFrom, dateTo);
    }
}
