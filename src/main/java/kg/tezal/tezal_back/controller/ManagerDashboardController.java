package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.StatisticsRestController;
import kg.tezal.tezal_back.model.StatisticsModel;
import kg.tezal.tezal_back.service.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("dashboard")
public class ManagerDashboardController {
    private final StatisticsRestController statisticsRestController;

    public ManagerDashboardController(StatisticsRestController statisticsRestController) {
        this.statisticsRestController = statisticsRestController;
    }

    @GetMapping
    public String main(Model model) {
        Long id = getUser().getOrganization().getId();
        model.addAttribute("orgId", id);
        return "dashboard";
    }

    private UserPrincipal getUser() {
        Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal user = (UserPrincipal) object;
        return user;
    }

    @ResponseBody
    @GetMapping("/sold/{orgId}")
    public List<StatisticsModel> getSoldProductsByOrgId(@PathVariable Long orgId){
        return statisticsRestController.getSoldProductsByOrgId(orgId);
    }

    @ResponseBody
    @GetMapping("/order/{orgId}")
    public List<StatisticsModel> getOrderByOrgId(@PathVariable Long orgId, @RequestParam("dateFrom") String dateFrom,
                                                        @RequestParam("dateTo")String dateTo){
        return statisticsRestController.getOrdersByOrgId(orgId, dateFrom, dateTo);
    }

    @ResponseBody
    @GetMapping("/user/{orgId}")
    public List<StatisticsModel> getUserByOrgId(@PathVariable Long orgId, @RequestParam("dateFrom") String dateFrom,
                                                 @RequestParam("dateTo")String dateTo){
        return statisticsRestController.getUsersByOrgId(orgId, dateFrom, dateTo);
    }
}
