package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.BalanceHistoryRestController;
import kg.tezal.tezal_back.apicontroller.OrgBonusTypeRestController;
import kg.tezal.tezal_back.apicontroller.UserRestController;
import kg.tezal.tezal_back.entity.User;
import kg.tezal.tezal_back.enums.OperationType;
import kg.tezal.tezal_back.service.CashierService;
import kg.tezal.tezal_back.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/cashier")
@Controller
public class CashierController {
    @Autowired
    private OrgBonusTypeRestController orgBonusTypeRestController;
   @Autowired
    private BalanceHistoryRestController balanceHistoryRestController;
    @Autowired
    private UserRestController userRestController;
    @Autowired
    private CashierService cashierService;

    private Long orgId;
    private Long userId;

    @GetMapping
    public String operationPage(Model model){
        getUserDetails();

        List<OrgBonusTypeModel> orgBonusTypeList = orgBonusTypeRestController.findAllOrgBonusTypeDto();
        model.addAttribute("orgBonusTypeList", orgBonusTypeList);
        model.addAttribute("orgId", orgId);
        model.addAttribute("confirmBalance", new BalanceConfirmModel());
        return "cashierOperation";
    }

    @GetMapping("/history")
    public String getHistory(Model model){
        if (userId == null)
            getUserDetails();
        List<BalanceHistoryLongModel> list = balanceHistoryRestController.getBalanceHistoryByUserId(userId, null, null);
        model.addAttribute("history", list);
        return "cashierHistory";
    }
    @GetMapping("/history/filter")
    public String getHistoryWithFilter(@RequestParam("dateFrom") String dateFrom,
                                       @RequestParam("dateTo") String dateTo, Model model){
        if (userId == null)
            getUserDetails();

        List<BalanceHistoryLongModel> list;
        if (dateFrom.isEmpty() || dateTo.isEmpty()){
            list = balanceHistoryRestController.getBalanceHistoryByUserId(userId, null, null);
        } else {
            list = balanceHistoryRestController.getBalanceHistoryByUserId(userId, dateFrom, dateTo);
        }
        model.addAttribute("history", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        return "cashierHistory";
    }

    @GetMapping("/create")
    public String confirm(@Valid @ModelAttribute("confirmBalance") BalanceConfirmModel balanceConfirm,
                           Model model){
        BonusValueModel bonusValueModel = cashierService.getBonusValueByOrgIdAndTypeId(orgId, balanceConfirm.getTypeId());
        model.addAttribute("organization", bonusValueModel);
        model.addAttribute("confirmBalance",balanceConfirm);
        System.out.println("Operation-----------------------"+ balanceConfirm);
        return "cashierConfirmPage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("confirmBalance") BalanceConfirmModel balanceConfirm, Model model){
        System.out.println("Operation-----------------------"+ balanceConfirm);
        BonusValueModel bonusValueModel = cashierService.getBonusValueByOrgIdAndTypeId(orgId, balanceConfirm.getTypeId());
        if (balanceConfirm.getPoint()>0){
            boolean res = cashierService.updateBalance(balanceConfirm.getPoint() , balanceConfirm.getClientId(), bonusValueModel.getBonusId());
            if (!res){
                model.addAttribute("confirmBalance",balanceConfirm);
                return "cashierConfirmPage";
            }
            cashierService.insertBalanceHistory(orgId, userId, balanceConfirm, bonusValueModel, OperationType.CREDIT);
        }
        cashierService.addBonusToBalance(orgId, userId, balanceConfirm,bonusValueModel.getValue());
        return "redirect:/cashier";
    }

    private void getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRestController.getUserByUserName(username);
        userId = user.getId();
        orgId = user.getOrganization().getId();
    }

    @ResponseBody()
    @RequestMapping(value = "/check", method = RequestMethod.GET,
            produces = "application/json")
    public  List<String> check(@RequestParam Long clientId, @RequestParam Long tId, @RequestParam Double point){
        List<HistoryModel> balance = cashierService.findBalanceByClientAndOrgAndBonusTypeId(clientId, orgId, tId);
        List<String> result = new ArrayList<>();
        if ((balance.get(0).getTotal() / 2 - point) >= 0){
            result.add("SUCCESS");
        } else{
            result.add("FAIL");
        }
        return result;
    }


}
