package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.OrgCategory;
import kg.tezal.tezal_back.model.OrgCategoryImageModel;
import kg.tezal.tezal_back.model.OrgCategoryModel;
import kg.tezal.tezal_back.service.OrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orgCategory")
public class OrgCategoryRestController {
    @Autowired
    private OrgCategoryService orgCategoryService;

    @GetMapping("/{id}")
    public OrgCategory getOrgCategoryById(@PathVariable("id") Long id) {
        return orgCategoryService.findById(id);
    }

//    @GetMapping("/all")
//    public List<OrgCategory> getAll() {
//        return orgCategoryService.findAll();
//    }
    @GetMapping("/all")
    public List<OrgCategoryModel> getAll() {
        return orgCategoryService.listAll();
    }

    @PutMapping("/{id}")
    public OrgCategory putOrgCategory(@PathVariable("id") Long id, @RequestBody OrgCategoryImageModel orgCategory) {
        return orgCategoryService.putById(id, orgCategory);
    }

    @PostMapping()
    public OrgCategory postOrgCategory(@RequestBody OrgCategoryImageModel orgCategory) {
        return orgCategoryService.create(orgCategory);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return orgCategoryService.deleteById(id);
    }

}
