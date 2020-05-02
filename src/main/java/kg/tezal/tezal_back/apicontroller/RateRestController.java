package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Rate;
import kg.tezal.tezal_back.model.RateModel;
import kg.tezal.tezal_back.service.RateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("rate")
@RestController
public class RateRestController {
    private final RateService rateService;

    public RateRestController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/{id}")
    public Rate findById(@PathVariable Long id) {
        return rateService.findById(id);
    }
    @GetMapping("/model/{id}")
    public RateModel getById(@PathVariable Long id) {
        return rateService.getById(id);
    }
    @GetMapping("/model/list/{orgId}")
    public List<RateModel> findAllByOrgId(@PathVariable Long orgId) {
        return rateService.findAllByOrgId(orgId);
    }
    @PostMapping
    public Rate create(@RequestBody RateModel rateModel) {
        return rateService.create(rateModel);
    }

    @GetMapping("/delete/{id}")
    public String deleteById(Long id) {
        return rateService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Rate putById(@PathVariable Long id, @RequestBody RateModel rateModel){
        return rateService.putById(id, rateModel);
    }

    @GetMapping("/checkOrder")
    public boolean isEnoughOrderInStock(@RequestParam("orgId") Long orgId, @RequestParam("matId")Long matId,
                                        @RequestParam("amount") Float count) {
        return rateService.isEnoughOrderInStock(orgId, matId, count);
    }

    @PostMapping("/decrease/{orgId}/{matId}")
    public void decreaseAmountMaterial(@PathVariable("orgId") Long orgId, @PathVariable("matId") Long matId,
                                       @RequestParam Float count) {
        rateService.decreaseAmountMaterial(orgId , matId, count);
    }

    @PostMapping("/increase/{orgId}/{matId}")
    public void increaseAmountMaterial(@PathVariable("orgId") Long orgId, @PathVariable("matId") Long matId,
                                       @RequestParam Float count){
        rateService.increaseAmountMaterial(orgId, matId, count);
    }
}
