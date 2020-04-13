package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.OrgBonus;
import kg.tezal.tezal_back.model.BonusShortModel;
import kg.tezal.tezal_back.service.OrgBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orgBonus")
public class OrgBonusRestController {
    @Autowired
    private OrgBonusService orgBonusService;

    @GetMapping("/{id}")
    public OrgBonus getOrgBonusById(@PathVariable("id") Long id) {
        return orgBonusService.findById(id);
    }

    @GetMapping("/all")
    public List<OrgBonus> getAll() {
        return orgBonusService.findAll();
    }

    @PutMapping("/{id}")
    public OrgBonus putOrgBonus(@PathVariable("id") Long id, @RequestBody OrgBonus orgBonus) {
        return orgBonusService.putById(id, orgBonus);
    }

    @PostMapping()
    public OrgBonus postOrgBonus(@RequestBody OrgBonus orgBonus) {
        orgBonusService.create(orgBonus);
        return orgBonus;
    }


    @GetMapping(value = "/changeStatus/{bonusId}")
    public void changeBonusStatus(@PathVariable("bonusId") Long bonusId){
        orgBonusService.changeStatus(bonusId);
    }


    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return orgBonusService.deleteById(id);
    }

    @GetMapping("/all/{id}")
    public List<BonusShortModel> findAllOrgBonusList(@PathVariable("id") Long id,
                                                     @RequestParam(value = "last_i", defaultValue="0", required = false) Long bonusId,
                                                     @RequestParam(value = "last_v", defaultValue = "0", required = false) Integer validity,
                                                     @RequestParam(value = "limit", defaultValue = "7", required = false) Integer pageSize) {
        return orgBonusService.getAllOrgBonusListByOrgId(id, validity, bonusId, pageSize);
    }
}
