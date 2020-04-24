package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.entity.Unit;
import kg.tezal.tezal_back.service.UnitService;
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
@RequestMapping("unit")
public class UnitController {
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping(value = "/list")
    public String getUnitList(
            @PageableDefault(7) Pageable pageable,
            Model model) {
        Page<Unit> units;
        units = unitService.findAll(pageable);
        model.addAttribute("units", units);
        return "unitList";
    }

    @GetMapping(value = "/{id}")
    public String unitGet(@PathVariable(required = false) Long id, Model model) {
        Unit unit = unitService.findById(id);
        model.addAttribute("unit", unit);
        model.addAttribute("add", false);
        return "unitForm";
    }

    @PostMapping("/update/{id}")
    public String updateUnit(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("unit") Unit unit,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            unit.setId(id);
            model.addAttribute(unit);
            model.addAttribute("add", false);
            return "unitForm";
        }
        unitService.putById(id, unit);
        return "redirect:/unit/list";
    }

    @GetMapping(value = "/form")
    public String unitForm(Model model) {
        Unit unit = new Unit();
        model.addAttribute("unit", unit);
        model.addAttribute("add", true);
        return "unitForm";
    }

    @PostMapping(value = "/create")
    public String createUnit(@Valid @ModelAttribute("unit") Unit unit,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(unit);
            model.addAttribute("add", true);
            return "unitForm";
        }
        unitService.create(unit);
        return "redirect:/unit/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrgCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            unitService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            return "unitForm";
        }
        return "redirect:/unit/list";
    }
}
