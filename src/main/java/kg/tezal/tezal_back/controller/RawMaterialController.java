package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.MaterialCategoryRestController;
import kg.tezal.tezal_back.apicontroller.UnitRestController;
import kg.tezal.tezal_back.entity.MaterialCategory;
import kg.tezal.tezal_back.entity.Unit;
import kg.tezal.tezal_back.model.RawMaterialModel;
import kg.tezal.tezal_back.service.RawMaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("product/")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;
    private final UnitRestController unitRestController;
    private final MaterialCategoryRestController materialCategoryRestController;
    private List<Unit> units;
    private List<MaterialCategory> categories;

    public RawMaterialController(RawMaterialService rawMaterialService, UnitRestController unitRestController, MaterialCategoryRestController materialCategoryRestController) {
        this.rawMaterialService = rawMaterialService;
        this.unitRestController = unitRestController;
        this.materialCategoryRestController = materialCategoryRestController;
    }

    @GetMapping(value = "/list")
    public String materialList(@RequestParam(value = "search", required = false) String search,
            @PageableDefault(7) Pageable pageable, Model model) {
        Page<RawMaterialModel> materials = rawMaterialService.findAllByNameOrDescription(search != null ? search.toLowerCase() : " ", pageable);
        boolean isEmpty = materials.isEmpty();
        model.addAttribute("materials", materials);
        model.addAttribute("isEmpty", isEmpty);
        model.addAttribute("searchResult", search);
        return "materialList";
    }

    @GetMapping(value = "/{id}")
    public String materialDetail(@PathVariable(required = false) Long id, Model model) {
        RawMaterialModel material = rawMaterialService.getRawMaterialById(id);
        model.addAttribute("material", material);
        model.addAttribute("categories", loadCategories());
        model.addAttribute("units", loadUnits());
        model.addAttribute("add", false);
        return "materialForm";
    }

    @PostMapping("update/{id}")
    public String updateMaterial(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute("material") RawMaterialModel materialModel,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(materialModel);
            model.addAttribute("categories", loadCategories());
            model.addAttribute("units", loadUnits());
            model.addAttribute("add", false);
            return "materialForm";
        }
        rawMaterialService.putById(id, materialModel);
        return "redirect:/product/list";
    }


    @GetMapping(value = "/form")
    public String materialForm(Model model) {
        RawMaterialModel material = new RawMaterialModel();
        model.addAttribute("material", material);
        model.addAttribute("categories", loadCategories());
        model.addAttribute("units", loadUnits());
        model.addAttribute("add", true);
        return "materialForm";
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addMaterial(@Valid @ModelAttribute("material") RawMaterialModel materialModel,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(materialModel);
            model.addAttribute("categories", loadCategories());
            model.addAttribute("units", loadUnits());
            model.addAttribute("add", true);
            return "materialForm";
        }
        rawMaterialService.create(materialModel);
        return "redirect:/product/list";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteMaterial(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            rawMaterialService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            redirectAttributes.addFlashAttribute("exception_text", "Couldn't delete on table material violates foreign key constraint ");
            return "redirect:/product/" + id;
        }
        return "redirect:/product/list";
    }

    private List<Unit> loadUnits(){
        return units == null ? unitRestController.getAll() : units;
    }

    private List<MaterialCategory> loadCategories(){
        return categories == null ? materialCategoryRestController.getAll() : categories;
    }

}
