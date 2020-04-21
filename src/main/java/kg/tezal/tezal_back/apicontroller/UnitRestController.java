package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Unit;
import kg.tezal.tezal_back.service.UnitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/unit")
public class UnitRestController {

    private final UnitService unitService;

    public UnitRestController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping("/{id}")
    public Unit getUnitById(@PathVariable("id") Long id) {
        return unitService.findById(id);
    }

    @GetMapping("/all")
    public List<Unit> getAll() {
        return unitService.findAll();
    }

    @PutMapping("/{id}")
    public Unit putUnit(@PathVariable("id") Long id, @RequestBody Unit unit) {
        return unitService.putById(id, unit);
    }

    @PostMapping()
    public Unit postUnit(@RequestBody Unit unit) {
        unitService.create(unit);
        return unit;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return unitService.deleteById(id);
    }

}
