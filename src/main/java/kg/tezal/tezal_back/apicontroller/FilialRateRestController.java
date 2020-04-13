package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.FilialRate;
import kg.tezal.tezal_back.service.FilialRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/filialRate")
public class FilialRateRestController {
    @Autowired
    private FilialRateService filialRateService;

    @GetMapping("/{id}")
    public FilialRate getFilialRateById(@PathVariable("id") Long id) {
        return filialRateService.findById(id);
    }

    @GetMapping("/all")
    public List<FilialRate> getAll() {
        return filialRateService.findAll();
    }

    @PutMapping("/{id}")
    public FilialRate putFilialRate(@PathVariable("id") Long id, @RequestBody FilialRate filialRate) {
        return filialRateService.putById(id, filialRate);
    }

    @PostMapping()
    public FilialRate postFilialRate(@RequestBody FilialRate filialRate) {
        filialRateService.create(filialRate);
        return filialRate;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return filialRateService.deleteById(id);
    }

}
