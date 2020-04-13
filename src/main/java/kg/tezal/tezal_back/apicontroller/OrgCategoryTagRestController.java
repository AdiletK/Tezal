package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.dao.OrgCategoryTagDao;
import kg.tezal.tezal_back.entity.OrgCategoryTag;
import kg.tezal.tezal_back.model.OrgCategoryTagModel;
import kg.tezal.tezal_back.service.OrgCategoryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orgCategoryTag")
public class OrgCategoryTagRestController {
    @Autowired
    OrgCategoryTagDao orgCategoryTagDao;
    @Autowired
    private OrgCategoryTagService orgCategoryTagService;

    @GetMapping("/{id}")
    public OrgCategoryTagModel getOrgCategoryTagById(@PathVariable("id") Long id) {
        return orgCategoryTagService.getTagById(id);
    }

    @GetMapping("/all")
    public List<OrgCategoryTag> getAll() {
        return orgCategoryTagService.findAll();
    }

    @GetMapping("/all/categoryId")
    public List<OrgCategoryTagModel> getAllByCategoryId(@PathVariable Long categoryId) {
        return orgCategoryTagDao.getTagsByOrgCategoryId(categoryId);
    }

//    @PutMapping("/{id}")
//    public OrgCategoryTag putOrgCategoryTag(@PathVariable("id") Long id, @RequestBody OrgCategoryTag orgCategoryTag) {
//        return orgCategoryTagService.putById(id, orgCategoryTag);
//    }

//    @PostMapping()
//    public OrgCategoryTag postOrgCategoryTag(@RequestBody OrgCategoryTag orgCategoryTag) {
//        orgCategoryTagService.create(orgCategoryTag);
//        return orgCategoryTag;
//    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return orgCategoryTagService.deleteById(id);
    }

}
