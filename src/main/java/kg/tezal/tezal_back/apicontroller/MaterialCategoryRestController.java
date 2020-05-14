package kg.tezal.tezal_back.apicontroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.tezal.tezal_back.entity.MaterialCategory;
import kg.tezal.tezal_back.service.MaterialCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Операции по взаимодействию с категориями товаров")
@CrossOrigin
@RestController
@RequestMapping("/prod_category")
public class MaterialCategoryRestController {

    private final MaterialCategoryService categoryService;

    public MaterialCategoryRestController(MaterialCategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @ApiOperation(value = "Получение категорий товара по id")
    @GetMapping("/{id}")
    public MaterialCategory getMaterialCategoryById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }
    @ApiOperation(value = "Получения списка всех категорий товаров")
    @GetMapping("/all")
    public List<MaterialCategory> getAll() {
        return categoryService.findAll();
    }

    @ApiOperation(value = "Обновление категории товара")
    @PutMapping("/{id}")
    public MaterialCategory putMaterialCategory(@PathVariable("id") Long id, @RequestBody MaterialCategory unit) {
        return categoryService.putById(id, unit);
    }

    @ApiOperation(value = "Создание категории товара")
    @PostMapping()
    public MaterialCategory postMaterialCategory(@RequestBody MaterialCategory unit) {
        categoryService.create(unit);
        return unit;
    }

    @ApiOperation(value = "Удаление категории товара")
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return categoryService.deleteById(id);
    }

}
