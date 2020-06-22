package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.OrderRestController;
import kg.tezal.tezal_back.apicontroller.PurchaseRestController;
import kg.tezal.tezal_back.apicontroller.UserRestController;
import kg.tezal.tezal_back.entity.User;
import kg.tezal.tezal_back.model.PurchaseShortModel;
import kg.tezal.tezal_back.model.SalesShortModel;
import kg.tezal.tezal_back.model.TypeOfReport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

//@PreAuthorize(value = "MANAGER")
@RequestMapping("organization/report")
@Controller
public class ReportController {
    private final PurchaseRestController purchaseRestController;
    private final UserRestController userRestController;
    private final OrderRestController orderRestController;
    private List<TypeOfReport> list;
    private Long orgId;

    public ReportController(PurchaseRestController purchaseRestController, UserRestController userRestController, OrderRestController orderRestController) {
        this.purchaseRestController = purchaseRestController;
        this.userRestController = userRestController;
        this.orderRestController = orderRestController;
    }

    @GetMapping
    public String init(Model model){
        model.addAttribute("typeOfReport", getTypeOfReport());
//        model.addAttribute("dateFrom", date());
//        model.addAttribute("dateTo", date());
        return "reportForm";
    }

    @ResponseBody
    @GetMapping("/getPurchase")
    public List<PurchaseShortModel> purchaseModels (@RequestParam("from") String dateFrom,
                                                    @RequestParam("to") String dateTo){
        if (orgId == null) getUserDetails();
        return purchaseRestController.findAllByOrgIdWithFilter(orgId, dateFrom, dateTo);
    }
    @ResponseBody
    @GetMapping("/getSales")
    public List<SalesShortModel> salesModels (@RequestParam("from") String dateFrom,
                                                 @RequestParam("to") String dateTo){
        if (orgId == null) getUserDetails();
        return orderRestController.findAllDeliveredByOrgIdWithFilter(orgId, dateFrom, dateTo);
    }

    private void getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRestController.getUserByUserName(username);
        orgId = user.getOrganization().getId();
    }

//    private String date() {
////        String pattern = "dd-MM-yyyy";
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
////        return simpleDateFormat.format(new Date());
//    }
    private List<TypeOfReport> getTypeOfReport() {
        if (list != null) return list;
        list = new ArrayList<>();
        list.add(new TypeOfReport(1L,"Покупки"));
        list.add(new TypeOfReport(2L,"Продажи"));
        return list;
    }
}
