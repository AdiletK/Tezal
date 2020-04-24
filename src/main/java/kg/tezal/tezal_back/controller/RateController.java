package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.RawMaterialRestController;
import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.RateModel;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.service.RateService;
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
public class RateController {

    private final RateService rateService;
    private final RawMaterialRestController materialRestController;
    private List<RawMaterialShortModel> materialModels;

    public RateController(RateService rateService, RawMaterialRestController materialRestController) {
        this.rateService = rateService;
        this.materialRestController = materialRestController;
    }

    @GetMapping(value = "{orgId}/rate/list")
    public String getList(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable, @PathVariable("orgId") Long orgId, Model model) {
        Page<RateModel> rates = rateService.findAllByOrgIdAndByNameOrDescription(orgId,search != null ? search.toLowerCase() : " ", pageable);
        model.addAttribute("orgId", orgId);
        model.addAttribute("search", search);
        model.addAttribute("rates", rates);
        return "rateList";
    }

    @GetMapping(value = "/rate/list/forOrgAdmin")
    public String getListForOrgAdmin(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable,
                          Authentication authentication, Model model) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Organization organization = user.getOrganization();
        Page<RateModel> rates = rateService.findAllByOrgIdAndByNameOrDescription(organization.getId(),search != null ? search.toLowerCase() : " ", pageable);
        model.addAttribute("orgId", organization.getId());
        model.addAttribute("search", search);
        model.addAttribute("rates", rates);
        return "rateList";
    }

    @GetMapping(value = "{orgId}/rate/form")
    public String rateForm(Model model) {
        RateModel rate = new RateModel();
        model.addAttribute("add", true);
        model.addAttribute("rate", rate);
        model.addAttribute("materials", loadMaterials());
        return "rateForm";
    }

    @GetMapping(value = "{orgId}/rate/{rateId}")
    public String getRateDetailPage(@PathVariable("rateId") Long rateId,@PathVariable("orgId") Long orgId, Model model) {
        RateModel rate = rateService.getById(rateId);
        model.addAttribute("rate", rate);
        model.addAttribute("orgId", orgId);
        model.addAttribute("materials", loadMaterials());
        model.addAttribute("add",false);
        return "rateForm";
    }

    @PostMapping(value = "{orgId}/rate/update/{rateId}")
    public String updateRate(@PathVariable("rateId") Long rateId, @PathVariable("orgId") Long orgId,
                               @Valid @ModelAttribute("rate") RateModel rate,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            rate.setId(orgId);
            model.addAttribute(rate);
            model.addAttribute("materials", loadMaterials());
            model.addAttribute("add",false);
            return "rateForm";
        }
        rateService.putById(rateId, rate);
        return "redirect:/organization/" + orgId + "/rate/list";
    }

    @GetMapping(value = "{orgId}/rate/delete/{rateId}")
    public String deleteRate(@PathVariable("rateId") Long rateId, @PathVariable("orgId") Long orgId) {
        rateService.deleteById(rateId);
        return "redirect:/organization/" + orgId + "/rate/list";
    }

    @PostMapping(value = "{orgId}/rate/create")
    public String createRate(@PathVariable("orgId") Long orgId, @Valid @ModelAttribute("rate") RateModel rateModel,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add",true);
            model.addAttribute("materials", loadMaterials());
            model.addAttribute(rateModel);
            return "rateForm";
        }
        rateModel.setOrganizationId(orgId);
        rateService.create(rateModel);
        return "redirect:/organization/" + orgId + "/rate/list";
    }
    private List<RawMaterialShortModel> loadMaterials(){
        return materialModels == null ? materialRestController.getMaterialsName() : materialModels;
    }
}
