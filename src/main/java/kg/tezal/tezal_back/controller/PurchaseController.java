package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.RateRestController;
import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.apicontroller.SupplierRestController;
import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.entity.Supplier;
import kg.tezal.tezal_back.model.PurchaseModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.service.PurchaseService;
import kg.tezal.tezal_back.service.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("organization/")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final RawMaterialRestController materialRestController;
    private final SupplierRestController supplierRestController;
    private final RateRestController rateRestController;

    private List<RawMaterialShortModel> materialList;
    private List<Supplier> supplierList;

    public PurchaseController(PurchaseService purchaseService, RawMaterialRestController materialRestController, SupplierRestController supplierRestController, RateRestController rateRestController) {
        this.purchaseService = purchaseService;
        this.materialRestController = materialRestController;
        this.supplierRestController = supplierRestController;
        this.rateRestController = rateRestController;
    }

    @GetMapping(value = "{orgId}/purchase/list")
    public String getList(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable, @PathVariable("orgId") Long orgId, Model model) {
        Page<PurchaseModel> purchases = purchaseService.findAllByOrgIdAndByNameOrDescription(orgId,search != null ? search.toLowerCase() : " ", pageable);
        model.addAttribute("orgId", orgId);
        model.addAttribute("search", search);
        model.addAttribute("purchases", purchases);
        return "purchaseList";
    }

    @GetMapping(value = "purchase/forOrgAdmin")
    public String getListForOrgAdmin(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable,
                          Authentication authentication, Model model) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Organization organization = user.getOrganization();
        Page<PurchaseModel> purchases = purchaseService.findAllByOrgIdAndByNameOrDescription(organization.getId(),search != null ? search.toLowerCase() : " ", pageable);
        model.addAttribute("orgId", organization.getId());
        model.addAttribute("search", search);
        model.addAttribute("purchases", purchases);
        return "purchaseList";
    }

    @GetMapping(value = "{orgId}/purchase/form")
    public String purchaseForm(Model model) {
        PurchaseModel purchase = new PurchaseModel();
        model.addAttribute("add", true);
        model.addAttribute("purchase", purchase);
        model.addAttribute("materials", loadMaterials());
        model.addAttribute("suppliers", loadSupplier());
        return "purchaseForm";
    }

    @GetMapping(value = "{orgId}/purchase/{purchaseId}")
    public String getFilialDetailPage(@PathVariable("purchaseId") Long purchaseId,@PathVariable("orgId") Long orgId, Model model) {
        PurchaseModel purchase = purchaseService.getById(purchaseId);
        model.addAttribute("purchase", purchase);
        model.addAttribute("orgId", orgId);
        model.addAttribute("materials", loadMaterials());
        model.addAttribute("suppliers", loadSupplier());
        model.addAttribute("add",false);
        return "purchaseForm";
    }

    @PostMapping(value = "{orgId}/purchase/update/{purchaseId}")
    public String updateFilial(@PathVariable("purchaseId") Long purchaseId, @PathVariable("orgId") Long orgId,
                               @Valid @ModelAttribute("purchase") PurchaseModel purchase,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            purchase.setId(purchaseId);
            model.addAttribute(purchase);
            model.addAttribute("materials", loadMaterials());
            model.addAttribute("suppliers", loadSupplier());
            model.addAttribute("add",false);
            return "purchaseForm";
        }
        purchaseService.putById(purchaseId, purchase);
        return "redirect:/organization/" + orgId + "/purchase/list";
    }

    @GetMapping(value = "{orgId}/purchase/delete/{purchaseId}")
    public String deleteFilial(@PathVariable("purchaseId") Long purchaseId, @PathVariable("orgId") Long orgId) {
        purchaseService.deleteById(purchaseId);
        return "redirect:/organization/" + orgId + "/purchase/list";
    }

    @PostMapping(value = "{orgId}/purchase/create")
    public String createFilial(@PathVariable("orgId") Long orgId, @Valid @ModelAttribute("purchase") PurchaseModel purchaseModel,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add",true);
            model.addAttribute("materials", loadMaterials());
            model.addAttribute("suppliers", loadSupplier());
            model.addAttribute(purchaseModel);
            return "purchaseForm";
        }
        purchaseModel.setOrganizationId(orgId);
        purchaseService.create(purchaseModel);
        return "redirect:/organization/" + orgId + "/purchase/list";
    }
    private List<RawMaterialShortModel> loadMaterials(){
        return materialList == null ? materialRestController.getMaterialsName() : materialList;
    }

    private List<Supplier> loadSupplier(){
        return supplierList == null ? supplierRestController.getAll() : supplierList;
    }
}
