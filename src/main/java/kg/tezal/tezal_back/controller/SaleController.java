package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.ClientRestController;
import kg.tezal.tezal_back.apicontroller.OrderMaterialRestController;
import kg.tezal.tezal_back.entity.Order;
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
public class SaleController {

    private final OrderService orderService;
    private final OrderMaterialRestController orderMaterialRestController;
    private final ClientRestController clientRestController;
    private List<RawMaterialShortModel> materialsName;

    public SaleController(OrderService orderService, OrderMaterialRestController orderMaterialRestController, ClientRestController clientRestController) {
        this.orderService = orderService;
        this.orderMaterialRestController = orderMaterialRestController;
        this.clientRestController = clientRestController;
    }

    @GetMapping(value = "{orgId}/sale/list")
    public String getList(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable, @PathVariable("orgId") Long orgId, Model model) {
        Page<OrderModel> sales = orderService.findAllByOrgIdAndByNameOrDescription(orgId,search != null ? search.toLowerCase() : "", pageable);
        List<OrderModel> l = orderService.findAllByOrgId(orgId);
        model.addAttribute("orgId", orgId);
        model.addAttribute("search", search);
        model.addAttribute("sales", sales);
        return "saleList";
    }

    @GetMapping(value = "sale/forOrgAdmin")
    public String getListForOrgAdmin(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable, Model model) {
        UserPrincipal user = getUser();
        System.out.println("Test--------"+user.getOrganization().getId());
        Page<OrderModel> sales = orderService.findAllDeliveredByOrgIdAndByNameOrDescription(user.getOrganization().getId(),search != null ? search.toLowerCase() : "", pageable);
        model.addAttribute("orgId", user.getOrganization().getId());
        model.addAttribute("search", search);
        model.addAttribute("sales", sales);
        return "saleList";
    }

    @GetMapping(value = "{orgId}/sale/form")
    public String saleForm(Model model) {
        OrderModel sale = new OrderModel();
        model.addAttribute("clients", clientRestController.getAll());
        model.addAttribute("add", true);
        model.addAttribute("sale", sale);
        return "saleForm";
    }

    @GetMapping(value = "{orgId}/sale/{saleId}")
    public String getOrderDetailPage(@PathVariable("saleId") Long saleId,@PathVariable("orgId") Long orgId, Model model) {
        OrderModel sale = orderService.getById(saleId);
        model.addAttribute("sale", sale);
        model.addAttribute("orgId", orgId);
        model.addAttribute("clients", clientRestController.getAll());
        model.addAttribute("add",false);
        return "saleForm";
    }
    @GetMapping(value = "{orgId}/sale/{saleId}/material/list")
    public String getOrdersMaterialDetailPage(@PathVariable("saleId") Long saleId,@PathVariable("orgId") Long orgId, Model model) {
        Order orderModel = orderService.findById(saleId);
        String client = orderModel.getClient().getFirstName() + " " + orderModel.getClient().getLastName();
        model.addAttribute("materials", orderMaterialRestController.getAll(saleId));
        model.addAttribute("client", client);
        return "saleMaterialList";
    }

    @PostMapping(value = "{orgId}/sale/update/{saleId}")
    public String updateOrder(@PathVariable("saleId") Long saleId, @PathVariable("orgId") Long orgId,
                               @Valid @ModelAttribute("sale") OrderModel sale,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            sale.setId(saleId);
            model.addAttribute(sale);
            model.addAttribute("add",false);
            return "saleForm";
        }
        sale.setUserId(getUser().getId());
        orderService.putById(saleId, sale);
        return "redirect:/organization/" + orgId + "/sale/list";
    }

    @GetMapping(value = "{orgId}/sale/delete/{saleId}")
    public String deleteOrder(@PathVariable("saleId") Long saleId, @PathVariable("orgId") Long orgId) {
        orderService.deleteById(saleId);
        return "redirect:/organization/" + orgId + "/sale/list";
    }

    @PostMapping(value = "{orgId}/sale/create")
    public String createOrder(@PathVariable("orgId") Long orgId, @Valid @ModelAttribute("sale") OrderModel saleModel,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add",true);
            model.addAttribute(saleModel);
            return "saleForm";
        }
        saleModel.setOrganizationId(orgId);
        saleModel.setUserId(getUser().getId());
        orderService.create(saleModel);
        return "redirect:/organization/" + orgId + "/sale/list";
    }

    private UserPrincipal getUser() {
        Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal user = (UserPrincipal) object;
        return user;
    }
}
