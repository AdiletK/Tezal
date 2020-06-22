package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.RawMaterial;
import kg.tezal.tezal_back.model.RawMaterialModel;
import kg.tezal.tezal_back.model.RawMaterialModelImage;
import kg.tezal.tezal_back.model.RawMaterialShortModel;
import kg.tezal.tezal_back.service.RawMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/raw_material")
public class RawMaterialRestController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialRestController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping("/{id}")
    public RawMaterial getRawMaterialById(@PathVariable("id") Long id) {
        return rawMaterialService.findById(id);
    }

    @GetMapping("/all")
    public List<RawMaterialModel> getAll() {
        return rawMaterialService.getRawMaterials();
    }

    @GetMapping("/all/short")
    public List<RawMaterialShortModel> getMaterialsName() {
        return rawMaterialService.getRawMaterialsName();
    }

    @PutMapping("/{id}")
    public RawMaterial putRawMaterial(@PathVariable("id") Long id, @RequestBody RawMaterialModelImage material) {
        return rawMaterialService.putById(id, material);
    }

    @PostMapping()
    public RawMaterial postRawMaterial(@RequestBody RawMaterialModelImage material) {
        return rawMaterialService.create(material);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return rawMaterialService.deleteById(id);
    }

}
