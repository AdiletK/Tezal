package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.MaterialCategory;
import kg.tezal.tezal_back.service.MaterialCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/prod_category")
public class MaterialCategoryRestController {

    private final MaterialCategoryService categoryService;

    public MaterialCategoryRestController(MaterialCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public MaterialCategory getMaterialCategoryById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/all")
    public List<MaterialCategory> getAll() {
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public MaterialCategory putMaterialCategory(@PathVariable("id") Long id, @RequestBody MaterialCategory unit) {
        return categoryService.putById(id, unit);
    }

    @PostMapping()
    public MaterialCategory postMaterialCategory(@RequestBody MaterialCategory unit) {
        categoryService.create(unit);
        return unit;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return categoryService.deleteById(id);
    }

}
