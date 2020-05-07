package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.OrgCategoryRestController;
import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.OrgCategoryModel;
import kg.tezal.tezal_back.model.OrganizationModel;
import kg.tezal.tezal_back.model.OrganizationModelImage;
import kg.tezal.tezal_back.service.OrganizationService;
import kg.tezal.tezal_back.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("organization/")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrgCategoryRestController orgCategoryRestController;

    @GetMapping(value = "/list")
    public String getOrganizationList(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault(7) Pageable pageable,
            Model model) {
        Page<OrganizationModel> organizations;
        if (search != null) {
            organizations = organizationService.findAllByNameOrDescription(search.toLowerCase(), pageable);
        } else {
            organizations = organizationService.findAll(pageable);
        }
        boolean isEmpty = organizations.isEmpty();
        model.addAttribute("organizations", organizations);
        model.addAttribute("isEmpty", isEmpty);
        model.addAttribute("searchResult", search);
        return "organizationList";
    }

    @GetMapping(value = "/{id}")
    public String organizationProfile(@PathVariable(required = false) Long id, Model model) {
        OrganizationModel organization = organizationService.getOrganizationById(id);
        List<OrgCategoryModel> orgCategories = orgCategoryRestController.getAll();
        model.addAttribute("organization", organization);
        model.addAttribute("orgCategories", orgCategories);
        model.addAttribute("add", false);
        return "organizationForm";

    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping(value = "/forOrgAdmin")
    public String organizationProfileForOrgAdmin(Model model, Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Organization organization = user.getOrganization();
        model.addAttribute("organization", organization);
        model.addAttribute("add", false);
        return "organizationForm";
    }

    @PostMapping("update/{id}")
    public String updateOrganization(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute("organization") OrganizationModelImage organizationModelImage,
                                     BindingResult result,
                                     Model model) {
        System.out.println(organizationModelImage.getImage().getOriginalFilename());
        if (result.hasErrors()) {
            List<OrgCategoryModel> orgCategories = orgCategoryRestController.getAll();
            model.addAttribute(organizationModelImage);
            model.addAttribute("orgCategories", orgCategories);
            model.addAttribute("add", false);
            System.out.println(organizationModelImage.getImage().getName());
            return "organizationForm";
        }
        organizationService.putById(id, organizationModelImage);
        return "redirect:/organization/list";
    }


    @GetMapping(value = "/form")
    public String organizationForm(Model model) {
        List<OrgCategoryModel> orgCategories = orgCategoryRestController.getAll();
        OrganizationModel organization = new OrganizationModel();
        model.addAttribute("organization", organization);
        model.addAttribute("orgCategories", orgCategories);
        model.addAttribute("add", true);
        return "organizationForm";
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addOrganization(@Valid @ModelAttribute("organization") OrganizationModelImage organizationModelImage,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<OrgCategoryModel> orgCategories = orgCategoryRestController.getAll();
            model.addAttribute(organizationModelImage);
            model.addAttribute("orgCategories", orgCategories);
            model.addAttribute("add", true);
            return "organizationForm";
        }
        organizationService.create(organizationModelImage);
        return "redirect:list";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteOrganization(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            organizationService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            redirectAttributes.addFlashAttribute("exception_text", "Couldn't delete on table organization violates foreign key constraint ");
            return "redirect:/organization/" + id;
        }
        return "redirect:/organization/list";
    }
}
