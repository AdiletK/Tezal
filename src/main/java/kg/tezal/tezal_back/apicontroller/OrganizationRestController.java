package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.OrganizationFullModel;
import kg.tezal.tezal_back.model.OrganizationModel;
import kg.tezal.tezal_back.model.OrganizationModelImage;
import kg.tezal.tezal_back.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/organization")
public class OrganizationRestController {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private FilialRestController filialRestController;
    @Autowired
    private OrgBonusRestController orgBonusRestController;
    @Autowired
    private EventRestController eventRestController;

    @GetMapping("/list/{id}")
    public List<OrganizationModel> getAllOrganizationByUserId(@PathVariable("id") Long id) {
        return organizationService.getOrganizationListByClientId(id);
    }

    @GetMapping("/next/{id}")
    public List<OrganizationModel> nextOrganizationListByClientIdAndCategoryId(@PathVariable("id") Long id,
                                                                               @RequestParam(value = "cat_i", defaultValue="1", required = false) Long categoryId,
                                                                               @RequestParam(value = "last_i", defaultValue = "1", required = false) Long lastId,
                                                                               @RequestParam(value = "limit", defaultValue = "4", required = false) Integer limit) {
        return organizationService.nextOrganizationListByClientIdAndCategoryId(id, categoryId, lastId , limit);
    }

    @GetMapping("/{id}")
    public Organization getOrganizationById(@PathVariable("id") Long id) {
        return organizationService.findById(id);
    }

    @GetMapping("/info/{id}")
    public OrganizationFullModel getOrganizationInfoById(@PathVariable("id") Long id) {
        return new OrganizationFullModel(organizationService.getOrganizationById(id), orgBonusRestController.findAllOrgBonusList(id, 0L, 0, 7), eventRestController.getAllEventsByOrgId(id,0L,"2020-03-03 00:00:00", 5), filialRestController.findAllFilialsByOrgId(id, 0L, 5.0,5));
    }

    @GetMapping(value = "/changeStatus/{id}")
    public void changeBonusStatus(@PathVariable("id") Long id){
        organizationService.changeStatus(id);
    }



    @GetMapping("/all")
    public List<Organization> getAll() {
        return organizationService.findAll();
    }

    @GetMapping("/allSorted")
    public List<Organization> getAllSorted() {
        return organizationService.findAllSorted();
    }

    @PutMapping("/{id}")
    public Organization putOrganization(@PathVariable("id") Long id, @RequestBody OrganizationModelImage organization) {
        return organizationService.putById(id, organization);
    }

    @PostMapping()
    public Organization postOrganization(@RequestBody OrganizationModelImage organization) {
        return organizationService.create(organization);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return organizationService.deleteById(id);
    }

    @GetMapping("/list")
    public List<OrganizationModel> getAllOrgs() {
        return organizationService.findAllOrganizationList();
    }
}
