package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.OrganizationRestController;
import kg.tezal.tezal_back.entity.Filial;
import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.FilialModel;
import kg.tezal.tezal_back.model.FilialModelImage;
import kg.tezal.tezal_back.service.FilialService;
import kg.tezal.tezal_back.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("organization/")
public class FilialController {

    @Autowired
    private FilialService filialService;

    @Autowired
    private OrganizationRestController organizationRestController;

    @GetMapping(value = "{orgId}/filial/list")
    public String getList(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable,
                          @PathVariable("orgId") Long orgId,
                          Model model) {
        Page<FilialModel> filials;
        if(search != null) {
            filials = filialService.findAllByOrgIdAndByNameOrDescription(orgId, search.toLowerCase(), pageable);
        } else {
            filials = filialService.findAllByOrgIdWithPagination(orgId, pageable);
            search = "";
        }
        model.addAttribute("orgId", orgId);
        model.addAttribute("search", search);
        model.addAttribute("filials", filials);
        return "filialList";
    }

    @GetMapping(value = "/filial/list/forOrgAdmin")
    public String getListForOrgAdmin(@RequestParam(value = "search" ,required = false) String search,
                          @PageableDefault(7) Pageable pageable,
                          Authentication authentication,
                          Model model) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Organization organization = user.getOrganization();
        Page<FilialModel> filials;
        if(search != null) {
            filials = filialService.findAllByOrgIdAndByNameOrDescription(organization.getId(), search.toLowerCase(), pageable);
        } else {
            filials = filialService.findAllByOrgIdWithPagination(organization.getId(), pageable);
            search = "";
        }
        model.addAttribute("orgId", organization.getId());
        model.addAttribute("search", search);
        model.addAttribute("filials", filials);
        return "filialList";
    }

    @GetMapping(value = "{orgId}/filial/form")
    public String filialForm(Model model) {
        Filial filial = new Filial();
        model.addAttribute("add", true);
        model.addAttribute("filial", filial);
        return "filialForm";
    }

    @GetMapping(value = "{orgId}/filial/{filId}")
    public String getFilialDetailPage(@PathVariable("filId") Long filId,@PathVariable("orgId") Long orgId, Model model) {
        Filial filial = filialService.findById(filId);
        model.addAttribute("filial", filial);
        model.addAttribute("orgId", orgId);
        model.addAttribute("add",false);
        return "filialForm";
    }

    @PostMapping(value = "{orgId}/filial/update/{filId}")
    public String updateFilial(@PathVariable("filId") Long filId, @PathVariable("orgId") Long orgId,
                               @Valid @ModelAttribute("filial") FilialModelImage filial,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            filial.setId(filId);
            model.addAttribute(filial);
            model.addAttribute("add", false);
            return "filialForm";
        }
        filial.setOrgId(organizationRestController.getOrganizationById(orgId).getId());
        filialService.putById(filId, filial);
        return "redirect:/organization/" + orgId + "/filial/list";
    }

    @GetMapping(value = "{orgId}/filial/delete/{filId}")
    public String deleteFilial(@PathVariable("filId") Long filId, @PathVariable("orgId") Long orgId) {
        filialService.deleteById(filId);
        return "redirect:/organization/" + orgId + "/filial/list";
    }

    @PostMapping(value = "{orgId}/filial/create")
    public String createFilial(@PathVariable("orgId") Long orgId, @Valid @ModelAttribute("filial") FilialModelImage filialModelImage,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add",true);
            model.addAttribute(filialModelImage);
            return "filialForm";
        }
        filialModelImage.setOrgId(orgId);
        filialService.create(filialModelImage);
        return "redirect:/organization/" + orgId + "/filial/list";
    }
}
