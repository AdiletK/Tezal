package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.ClientRestController;
import kg.tezal.tezal_back.apicontroller.OrderMaterialRestController;
import kg.tezal.tezal_back.model.OrderModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.service.OrderService;
import kg.tezal.tezal_back.service.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("organization/")
public class OrderController {

    private final OrderService orderService;
    private final OrderMaterialRestController orderMaterialRestController;
    private final ClientRestController clientRestController;
    private List<RawMaterialShortModel> materialsName;

    public OrderController(OrderService orderService, OrderMaterialRestController orderMaterialRestController, ClientRestController clientRestController) {
        this.orderService = orderService;
        this.orderMaterialRestController = orderMaterialRestController;
        this.clientRestController = clientRestController;
    }

    @GetMapping(value = "{orgId}/order/list")
    public String getList(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable, @PathVariable("orgId") Long orgId, Model model) {
        Page<OrderModel> orders = orderService.findAllByOrgIdAndByNameOrDescription(orgId,search != null ? search.toLowerCase() : "", pageable);
        List<OrderModel> l = orderService.findAllByOrgId(orgId);
        model.addAttribute("orgId", orgId);
        model.addAttribute("search", search);
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @GetMapping(value = "order/forOrgAdmin")
    public String getListForOrgAdmin(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable, Model model) {
        UserPrincipal user = getUser();
        System.out.println("Test--------"+user.getOrganization().getId());
        Page<OrderModel> orders = orderService.findAllByOrgIdAndByNameOrDescription(user.getOrganization().getId(),search != null ? search.toLowerCase() : "", pageable);
        model.addAttribute("orgId", user.getOrganization().getId());
        model.addAttribute("search", search);
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @GetMapping(value = "{orgId}/order/form")
    public String orderForm(Model model) {
        OrderModel order = new OrderModel();
        model.addAttribute("clients", clientRestController.getAll());
        model.addAttribute("add", true);
        model.addAttribute("order", order);
        return "orderForm";
    }

    @GetMapping(value = "{orgId}/order/{orderId}")
    public String getOrderDetailPage(@PathVariable("orderId") Long orderId,@PathVariable("orgId") Long orgId, Model model) {
        OrderModel order = orderService.getById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orgId", orgId);
        model.addAttribute("clients", clientRestController.getAll());
        model.addAttribute("add",false);
        return "orderForm";
    }

    @PostMapping(value = "{orgId}/order/update/{orderId}")
    public String updateOrder(@PathVariable("orderId") Long orderId, @PathVariable("orgId") Long orgId,
                               @Valid @ModelAttribute("order") OrderModel order,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            order.setId(orderId);
            model.addAttribute(order);
            model.addAttribute("add",false);
            return "orderForm";
        }
        order.setUserId(getUser().getId());
        orderService.putById(orderId, order);
        return "redirect:/organization/" + orgId + "/order/list";
    }

    @GetMapping(value = "{orgId}/order/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId, @PathVariable("orgId") Long orgId) {
        orderService.deleteById(orderId);
        return "redirect:/organization/" + orgId + "/order/list";
    }

    @PostMapping(value = "{orgId}/order/create")
    public String createOrder(@PathVariable("orgId") Long orgId, @Valid @ModelAttribute("order") OrderModel orderModel,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add",true);
            model.addAttribute(orderModel);
            return "orderForm";
        }
        orderModel.setOrganizationId(orgId);
        orderModel.setUserId(getUser().getId());
        orderService.create(orderModel);
        return "redirect:/organization/" + orgId + "/order/list";
    }

    private UserPrincipal getUser() {
        Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal user = (UserPrincipal) object;
        return user;
    }
}
