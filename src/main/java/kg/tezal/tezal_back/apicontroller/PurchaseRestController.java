package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Purchase;
import kg.tezal.tezal_back.model.PurchaseModel;
import kg.tezal.tezal_back.model.PurchaseShortModel;
import kg.tezal.tezal_back.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseRestController {
    private final PurchaseService purchaseService;

    public PurchaseRestController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{id}")
    public Purchase findById(@PathVariable Long id){
        return purchaseService.findById(id);
    }

    @GetMapping("/model/{id}")
    public PurchaseModel getById(@PathVariable Long id){
        return purchaseService.getById(id);
    }
    @GetMapping("/list/{orgId}")
    public List<PurchaseModel> findAllByOrgId(@PathVariable Long orgId){
        return purchaseService.findAllByOrgId(orgId);
    }

    @GetMapping("/list/filter/{orgId}")
    public List<PurchaseShortModel> findAllByOrgIdWithFilter(@PathVariable Long orgId, @RequestParam("dateFrom")String dateFrom,
                                                                   @RequestParam("dateTo") String dateTo){
        return purchaseService.findAllByOrgIdWithDate(orgId, dateFrom, dateTo);
    }

    @PostMapping
    public Purchase create(@RequestBody PurchaseModel purchaseModel){
        return purchaseService.create(purchaseModel);
    }

    @DeleteMapping("/{id}")
    public String deleteById(Long id) {
        return purchaseService.deleteById(id);
    }
    @PutMapping("/{id}")
    public Purchase putById(Long id, PurchaseModel purchaseModel){
        return purchaseService.putById(id, purchaseModel);
    }
}
