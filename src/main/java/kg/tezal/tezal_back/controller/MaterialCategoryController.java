package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.entity.MaterialCategory;
import kg.tezal.tezal_back.service.MaterialCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("material_category")
public class MaterialCategoryController {
    private final MaterialCategoryService categoryService;

    public MaterialCategoryController(MaterialCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/list")
    public String getUnitList(
            @PageableDefault(7) Pageable pageable, Model model) {
        Page<MaterialCategory> categories = categoryService.findAll(pageable);
        model.addAttribute("categories", categories);
        return "materialCategoryList";
    }

    @GetMapping(value = "/{id}")
    public String unitGet(@PathVariable(required = false) Long id, Model model) {
        MaterialCategory category = categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("add", false);
        return "materialCategoryForm";
    }

    @PostMapping("/update/{id}")
    public String updateUnit(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("category") MaterialCategory category,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(category);
            model.addAttribute("add", false);
            return "materialCategoryForm";
        }
        categoryService.putById(id, category);
        return "redirect:/material_category/list";
    }

    @GetMapping(value = "/form")
    public String unitForm(Model model) {
        MaterialCategory category = new MaterialCategory();
        model.addAttribute("category", category);
        model.addAttribute("add", true);
        return "materialCategoryForm";
    }

    @PostMapping(value = "/create")
    public String createUnit(@Valid @ModelAttribute("category") MaterialCategory category,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(category);
            model.addAttribute("add", true);
            return "materialCategoryForm";
        }
        categoryService.create(category);
        return "redirect:/material_category/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrgCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            return "materialCategoryForm";
        }
        return "redirect:/material_category/list";
    }
}
