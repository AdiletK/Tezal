package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.OrderMaterialRestController;
import kg.tezal.tezal_back.apicontroller.OrderRestController;
import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.model.OrderMaterialModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.service.OrderMaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("organization/{orgId}/order/{orderId}/material/")
public class OrderMaterialController {

    private final OrderMaterialService orderMaterialService;
    private final RawMaterialRestController materialRestController;
    private final OrderRestController orderRestController;
    private List<RawMaterialShortModel> materialsName;

    public OrderMaterialController(OrderMaterialService orderMaterialSer, OrderMaterialRestController orderMaterialService, RawMaterialRestController rawMaterialRestController, OrderRestController orderRestController) {
        this.orderMaterialService = orderMaterialSer;
        this.materialRestController = rawMaterialRestController;
        this.orderRestController = orderRestController;
    }

    @GetMapping("list")
    public String getOrderMaterialsByOrderId(@PathVariable("orderId")Long orderId,@PathVariable("orgId") Long orgId,
                                             Model model){
        Order orderModel = orderRestController.getOrderById(orderId);
        String client = orderModel.getClient().getFirstName() + " " + orderModel.getClient().getLastName();
        List<OrderMaterialModel> list = orderMaterialService.findAllByOrgId(orderId);
        Double sum = 0.0;
        for (OrderMaterialModel order : list) {
            sum += order.getSum();
        }
        model.addAttribute("materials", list);
        model.addAttribute("client", client);
        model.addAttribute("amount_sum", sum);
        return "orderMaterialList";
    }
    @GetMapping(value = "{matId}")
    public String getOrderMaterialDetailPage(@PathVariable("orderId") Long orderId,
                                             @PathVariable("orgId") Long orgId, @PathVariable("matId")Long matId,
                                             Model model) {
        OrderMaterialModel orderMaterialModel = orderMaterialService.getById(matId);
        loadAddAttributes(orderId, orgId, model, orderMaterialModel, false);
        return "orderMaterialForm";
    }
    @PostMapping(value = "update/{matId}")
    public String updateOrderMaterialDetailPage(@PathVariable("orderId") Long orderId, @PathVariable("orgId") Long orgId,
                                                @PathVariable("matId")Long matId, Model model,
                                                @Valid @ModelAttribute("orderMaterial") OrderMaterialModel orderMaterialModel,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            orderMaterialModel.setId(matId);
            loadAddAttributes(orderId, orgId, model, orderMaterialModel, false);
            return "orderMaterialForm";
        }
        orderMaterialService.putById(matId, orderMaterialModel);
        return "redirect:/organization/" + orgId + "/order/" + orderId + "/material/list";
    }

    @GetMapping(value = "form")
    public String getOrderMaterialPage(@PathVariable("orderId") Long orderId,@PathVariable("orgId") Long orgId,
                                       Model model) {
        OrderMaterialModel orderMaterial = new OrderMaterialModel();
        loadAddAttributes(orderId, orgId, model, orderMaterial, true);
        return "orderMaterialForm";
    }

    @PostMapping(value = "addMaterial")
    public String addOrderMaterialPage(@PathVariable("orderId") Long orderId,@PathVariable("orgId") Long orgId,
                                       Model model, @Valid @ModelAttribute("orderMaterial")OrderMaterialModel orderMaterialModel,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            loadAddAttributes(orderId, orgId, model, orderMaterialModel, true);
            return "orderMaterialForm";
        }
        orderMaterialService.create(orderMaterialModel);
        return "redirect:/organization/" + orgId + "/order/" + orderId + "/material/list";
    }
    @GetMapping("delete/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteOrderMaterial(@PathVariable("orderId") Long orderId, @PathVariable("orgId") Long orgId, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            orderMaterialService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            redirectAttributes.addFlashAttribute("exception_text", "Couldn't delete on table orderMaterial violates foreign key constraint ");
            return "redirect:/organization/" + orgId + "/order/" + orderId + "/material/" + id;
        }
        return "redirect:/organization/" + orgId + "/order/" + orderId + "/material/list";
    }

    private void loadAddAttributes(Long orderId, Long orgId, Model model, OrderMaterialModel orderMaterial, boolean b) {
        model.addAttribute("orderMaterial", orderMaterial);
        model.addAttribute("orgId", orgId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("materials", loadMaterials());
        model.addAttribute("add", b);
    }

    private List<RawMaterialShortModel> loadMaterials(){
        return  materialsName == null ? materialRestController.getMaterialsName()
                : materialsName;
    }
}
