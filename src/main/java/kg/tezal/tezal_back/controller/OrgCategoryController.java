package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.OrgCategoryTagRestController;
import kg.tezal.tezal_back.entity.OrgCategory;
import kg.tezal.tezal_back.model.OrgCategoryImageModel;
import kg.tezal.tezal_back.model.OrgCategoryModel;
import kg.tezal.tezal_back.model.OrgCategoryTagModel;
import kg.tezal.tezal_back.service.OrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("org_category")
public class OrgCategoryController {

    @Autowired
    private OrgCategoryTagRestController orgCategoryTagRestController;

    @Autowired
    private OrgCategoryService orgCategoryService;

    @GetMapping("/list")
    public String getOrganizationList(@PageableDefault(10) Pageable pageable,
                                      Model model) {
        Page<OrgCategoryModel> orgCategories = orgCategoryService.findAll(pageable);
        model.addAttribute("orgCategories", orgCategories);
        model.addAttribute("add", false);
        return "orgCategoryList";
    }

    @GetMapping("{id}")
    public String getOrgCategoryById(@PathVariable("id") Long id, Model model) {
        OrgCategoryModel orgCategoryModel = orgCategoryService.findOrgCategoryById(id);
        List<OrgCategoryTagModel> orgCategoryTags = orgCategoryTagRestController.getAllByCategoryId(id);
        List<String> tagNames = orgCategoryTags.stream().map(OrgCategoryTagModel::getName).collect(Collectors.toList());
        String separate = ",";
        String tags = String.join(separate, tagNames);
        model.addAttribute("tags", tags);
        model.addAttribute("category", orgCategoryModel);
        model.addAttribute("add", false);
        return "orgCategoryForm";
    }

    @PostMapping("/update/{id}")
    public String updateOrgCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") OrgCategoryImageModel orgCategoryModel,
                                    BindingResult result, Model model) {
        System.out.println(orgCategoryModel.getImage().getOriginalFilename());
        if (result.hasErrors()) {
            orgCategoryModel.setId(id);
            model.addAttribute(orgCategoryModel);
            return "orgCategoryForm";
        }
        orgCategoryService.putById(id, orgCategoryModel);
        return "redirect:/org_category/list";
    }

    @GetMapping(value = "/form")
    public String categoryForm(Model model) {
        OrgCategory orgCategory = new OrgCategory();
        model.addAttribute("category", orgCategory);
        model.addAttribute("add", true);
        return "orgCategoryForm";
    }

    @PostMapping(value = "/create")
    public String addOrgCategory(@Valid @ModelAttribute("category") OrgCategoryImageModel orgCategory,
                                 BindingResult result, Model model) {
        System.out.println(orgCategory.getImage().getOriginalFilename());
        if (result.hasErrors()) {
            model.addAttribute(orgCategory);
            model.addAttribute("add", true);
            return "orgCategoryForm";
        }
        orgCategoryService.create(orgCategory);
        return "redirect:/org_category/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrgCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            orgCategoryService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            redirectAttributes.addFlashAttribute("exception_text", "Couldn't delete on table org_category violates foreign key constraint " +
                    "on table \"organization\"");
            return "redirect:/org_category/" + id;
        }
        return "redirect:/org_category/list";
    }

}
