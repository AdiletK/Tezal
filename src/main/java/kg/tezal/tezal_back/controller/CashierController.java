package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.*;
import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.entity.User;
import kg.tezal.tezal_back.enums.OrderStatus;
import kg.tezal.tezal_back.model.OrderMaterialModel;
import kg.tezal.tezal_back.model.OrderModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/cashier")
@Controller
public class CashierController {
    private final OrderMaterialRestController orderMaterialRestController;
    private final OrderRestController orderRestController;
    private final UserRestController userRestController;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RawMaterialRestController materialRestController;
    @Autowired
    private ClientRestController clientRestController;
    @Autowired
    private RateRestController rateRestController;

    private List<RawMaterialShortModel> materialsName;

    private Long orgId;
    private Long userId;

    public CashierController(OrderMaterialRestController orderMaterialRestController, OrderRestController orderRestController, UserRestController userRestController) {
        this.orderMaterialRestController = orderMaterialRestController;
        this.orderRestController = orderRestController;
        this.userRestController = userRestController;
    }

    @GetMapping("/orders")
    public String operationPage(@RequestParam(value = "search" ,required = false, defaultValue = "") String search,
                          @PageableDefault(7) Pageable pageable, Model model) {
        getUserDetails();
        Page<OrderModel> orders = orderService.findAllByOrgIdAndByNameOrDescriptionForCashier(  orgId, search.toLowerCase() , pageable);
        model.addAttribute("orgId", orgId);
        model.addAttribute("search", search);
        model.addAttribute("orders", orders);
        return "orderList";
    }
    @GetMapping("/{id}/material/list")
    public String materialPage(@PathVariable Long id, Model model){
        Order orderModel = orderRestController.getOrderById(id);
        String client = orderModel.getClient().getFirstName() + " " + orderModel.getClient().getLastName();
        List<OrderMaterialModel> list = orderMaterialRestController.getAll(id);
        Double sum = 0.0;
        for (OrderMaterialModel order : list) {
            sum += order.getSum();
        }
        model.addAttribute("materials", orderMaterialRestController.getAll(id));
        model.addAttribute("client", client);
        model.addAttribute("amount_sum", sum);
        return "orderMaterialList";
    }

    @GetMapping("/{id}/confirm")
    public String confirmMethod(@PathVariable Long id){
        changeStatus(id, OrderStatus.ACCEPT);
        return "redirect:material/list";
    }

    @GetMapping("/{id}/decline")
    public String declinedMethod(@PathVariable Long id){
        changeStatus(id, OrderStatus.DECLINED);
        return "redirect:/cashier/orders";
    }

    @GetMapping("/{id}/ready")
    public String readyMethod(@PathVariable Long id){
        changeStatus(id, OrderStatus.READY);
        return "redirect:/cashier/orders";
    }

    @GetMapping("/{id}/delivered")
    public String deliveredMethod(@PathVariable Long id){
        changeStatus(id, OrderStatus.DELIVERED);
        return "redirect:/cashier/orders";
    }

    @GetMapping("/history")
    public String getHistory(@RequestParam(value = "search" ,required = false, defaultValue = "") String search,
                             @PageableDefault(7) Pageable pageable, Model model){
        if (userId == null) getUserDetails();
        Page<OrderModel> historyModel = orderService.findAllByUserIdAndByNameOrDescriptionForCashier(userId, search.toLowerCase(), pageable);
        model.addAttribute("orders", historyModel);
        model.addAttribute("search", search);
        return "cashierHistory";
    }

    @GetMapping("/history/filter")
    public String getHistoryWithFilter(@RequestParam("dateFrom") String dateFrom,
                                       @RequestParam("dateTo") String dateTo, Model model){
        if (userId == null)
            getUserDetails();

//        List<BalanceHistoryLongModel> list;
        if (dateFrom.isEmpty() || dateTo.isEmpty()){
//            list = balanceHistoryRestController.getBalanceHistoryByUserId(userId, null, null);
        } else {
//            list = balanceHistoryRestController.getBalanceHistoryByUserId(userId, dateFrom, dateTo);
        }
//        model.addAttribute("history", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        return "cashierHistory";
    }

    @GetMapping("/{id}/material/{matId}")
    public String getOrderMaterialDetailPage(@PathVariable("id") Long orderId, @PathVariable("matId")Long matId,
                                             Model model) {
        OrderMaterialModel orderMaterialModel = orderMaterialRestController.getOrderMaterialModelById(matId);
        loadAddAttributes(orderId, orgId, model, orderMaterialModel, false);
        return "orderMaterialForm";
    }

    @PostMapping(value = "/{id}/material/update/{matId}")
    public String updateOrderMaterialDetailPage(@PathVariable("id") Long orderId,
                                                @PathVariable("matId")Long matId, Model model,
                                                @Valid @ModelAttribute("orderMaterial") OrderMaterialModel orderMaterialModel,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            orderMaterialModel.setId(matId);
            loadAddAttributes(orderId, orgId, model, orderMaterialModel, false);
            return "orderMaterialForm";
        }
        orderMaterialModel.setOrderId(orderId);
        orderMaterialRestController.putOrderMaterial(matId, orderMaterialModel);
        return "redirect:/cashier/" + orderId + "/material/list";
    }
    @GetMapping("/{id}/material/form")
    public String getOrderMaterialPage(@PathVariable("id") Long orderId, Model model) {
        OrderMaterialModel orderMaterial = new OrderMaterialModel();
        loadAddAttributes(orderId, orgId, model, orderMaterial, true);
        return "orderMaterialForm";
    }

    @PostMapping(value = "/{id}/material/addMaterial")
    public String addOrderMaterialPage(@PathVariable("id") Long orderId, Model model,
                                       @Valid @ModelAttribute("orderMaterial")OrderMaterialModel orderMaterialModel,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            loadAddAttributes(orderId, orgId, model, orderMaterialModel, true);
            return "orderMaterialForm";
        }
        orderMaterialModel.setOrderId(orderId);
        orderMaterialRestController.postOrderMaterial(orderMaterialModel);
        rateRestController.decreaseAmountMaterial(orgId, orderMaterialModel.getId(), orderMaterialModel.getCount().floatValue());
        return "redirect:/cashier/" + orderId + "/material/list";
    }
    @ResponseBody
    @GetMapping("/check")
    public boolean isEnoughOrderInStock(@RequestParam("matId") Long matId,
                                        @RequestParam("amount") Float count) {
        if (orgId == null){getUserDetails();}
        return rateRestController.isEnoughOrderInStock(orgId, matId, count);
    }

    @GetMapping("/{orderId}/material/delete/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteOrderMaterial(@PathVariable("orderId") Long orderId, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            orderMaterialRestController.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            redirectAttributes.addFlashAttribute("exception_text", "Couldn't delete on table orderMaterial violates foreign key constraint ");
            return "redirect:/cashier/" + orderId + "/material/" + id;
        }
        return "redirect:/cashier/" + orderId + "/material/list";
    }
    @GetMapping("/form")
    public String orderForm(Model model) {
        OrderModel order = new OrderModel();
        model.addAttribute("clients", clientRestController.getAll());
        model.addAttribute("add", true);
        model.addAttribute("order", order);
        return "orderForm";
    }

    @PostMapping(value = "/create")
    public String createOrder(@Valid @ModelAttribute("order") OrderModel orderModel,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add",true);
            model.addAttribute(orderModel);
            return "orderForm";
        }
        if (orgId == null || userId == null)
            getUserDetails();
        orderModel.setOrganizationId(orgId);
        orderModel.setUserId(userId);
        orderService.create(orderModel);
        return "redirect:/cashier/orders";
    }

    private void loadAddAttributes(Long orderId, Long orgId, Model model, OrderMaterialModel orderMaterial, boolean b) {
        model.addAttribute("orderMaterial", orderMaterial);
        model.addAttribute("orgId", orgId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("materials", loadMaterials());
        model.addAttribute("add", b);
    }

    private void changeStatus(Long id, OrderStatus status) {
        if (userId == null) getUserDetails();
        OrderModel orderModel = orderRestController.getOrderModelById(id);
        orderModel.setOrdersStatus(status);
        orderModel.setUserId(userId);
        orderRestController.putOrder(id, orderModel);
    }

    private List<RawMaterialShortModel> loadMaterials(){
        return  materialsName == null ? materialRestController.getMaterialsName()
                : materialsName;
    }

    private void getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRestController.getUserByUserName(username);
        userId = user.getId();
        orgId = user.getOrganization().getId();
    }

}
