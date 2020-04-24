package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.entity.Supplier;
import kg.tezal.tezal_back.service.SupplierService;
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
@RequestMapping("supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping(value = "/list")
    public String getUnitList(
            @PageableDefault(7) Pageable pageable,
            Model model) {
        Page<Supplier> suppliers;
        suppliers = supplierService.findAll(pageable);
        model.addAttribute("suppliers", suppliers);
        return "supplierList";
    }

    @GetMapping(value = "/{id}")
    public String unitGet(@PathVariable(required = false) Long id, Model model) {
        Supplier supplier = supplierService.findById(id);
        model.addAttribute("supplier", supplier);
        model.addAttribute("add", false);
        return "supplierForm";
    }

    @PostMapping("/update/{id}")
    public String updateUnit(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("supplier") Supplier supplier,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            supplier.setId(id);
            model.addAttribute(supplier);
            model.addAttribute("add", false);
            return "unitForm";
        }
        supplierService.putById(id, supplier);
        return "redirect:/supplier/list";
    }

    @GetMapping(value = "/form")
    public String unitForm(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        model.addAttribute("add", true);
        return "supplierForm";
    }

    @PostMapping(value = "/create")
    public String createUnit(@Valid @ModelAttribute("supplier") Supplier supplier,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(supplier);
            model.addAttribute("add", true);
            return "supplierForm";
        }
        supplierService.create(supplier);
        return "redirect:/supplier/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrgCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            supplierService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            return "supplierForm";
        }
        return "redirect:/supplier/list";
    }
}
