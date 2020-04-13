package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Filial;
import kg.tezal.tezal_back.model.FilialModel;
import kg.tezal.tezal_back.model.FilialModelImage;
import kg.tezal.tezal_back.model.FilialShortModel;
import kg.tezal.tezal_back.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/filial")
public class FilialRestController {
    @Autowired
    private FilialService filialService;

    @GetMapping("/{id}")
    public Filial getFilialById(@PathVariable("id") Long id) {
        return filialService.findById(id);
    }

    @PutMapping("/{id}")
    public Filial putFilial(@PathVariable("id") Long id, @RequestBody FilialModelImage filial) {
        return filialService.putById(id, filial);
    }

    @PostMapping()
    public Filial postFilial(@RequestBody FilialModelImage filial) {
        return filialService.create(filial);
    }


    @GetMapping(value = "/changeStatus/{id}")
    public void changeBonusStatus(@PathVariable("id") Long id){
        filialService.changeStatus(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return filialService.deleteById(id);
    }

    @GetMapping("/allByOrgId/{id}")
    public List<FilialShortModel> findAllFilialsByOrgId(@PathVariable("id") Long id,
                                                        @RequestParam(value = "last_i", defaultValue="0", required = false) Long lastId,
                                                        @RequestParam(value = "last_a", defaultValue = "5", required = false) Double lastAverage,
                                                        @RequestParam(value = "limit", defaultValue = "5", required = false) Integer pageSize) {
        return filialService.getAllFilialsByOrgId(id, lastId, lastAverage, pageSize);
    }

    @GetMapping("/all")
    public List<FilialModel> findAllByOrgId(@Param("id") Long id) {
        return filialService.findAllByOrgId(id);
    }

}
