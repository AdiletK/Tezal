package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Supplier;
import kg.tezal.tezal_back.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/supplier")
public class SupplierRestController {

    private final SupplierService supplierService;


    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public Supplier findById(@PathVariable("id") Long id) {
        return supplierService.findById(id);
    }

    @GetMapping("/all")
    public List<Supplier> getAll() {
        return supplierService.findAll();
    }

    @PutMapping("/{id}")
    public Supplier putById(@PathVariable("id") Long id, @RequestBody Supplier supplier) {
        return supplierService.putById(id, supplier);
    }

    @PostMapping()
    public Supplier create (@RequestBody Supplier supplier) {
        return supplierService.create(supplier);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return supplierService.deleteById(id);
    }

}
