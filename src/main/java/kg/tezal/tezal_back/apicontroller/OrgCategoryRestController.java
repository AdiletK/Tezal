package kg.tezal.tezal_back.apicontroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.tezal.tezal_back.entity.OrgCategory;
import kg.tezal.tezal_back.model.OrgCategoryImageModel;
import kg.tezal.tezal_back.model.OrgCategoryModel;
import kg.tezal.tezal_back.service.OrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Операции по взаимодействию с категориями организации")
@CrossOrigin
@RestController
@RequestMapping("/orgCategory")
public class OrgCategoryRestController {
    @Autowired
    private OrgCategoryService orgCategoryService;
    @ApiOperation(value = "Получение категорий организации по id")
    @GetMapping("/{id}")
    public OrgCategory getOrgCategoryById(@PathVariable("id") Long id) {
        return orgCategoryService.findById(id);
    }

//    @GetMapping("/all")
//    public List<OrgCategory> getAll() {
//        return orgCategoryService.findAll();
//    }
    @ApiOperation(value = "Получения списка всех категорий организации")
    @GetMapping("/all")
    public List<OrgCategoryModel> getAll() {
        return orgCategoryService.listAll();
    }

    @ApiOperation(value = "Обновление категории организации")
    @PutMapping("/{id}")
    public OrgCategory putOrgCategory(@PathVariable("id") Long id, @RequestBody OrgCategoryImageModel orgCategory) {
        return orgCategoryService.putById(id, orgCategory);
    }
    @ApiOperation(value = "Создание категории организации")
    @PostMapping()
    public OrgCategory postOrgCategory(@RequestBody OrgCategoryImageModel orgCategory) {
        return orgCategoryService.create(orgCategory);
    }
    @ApiOperation(value = "Удаление категории организации")
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return orgCategoryService.deleteById(id);
    }

}
