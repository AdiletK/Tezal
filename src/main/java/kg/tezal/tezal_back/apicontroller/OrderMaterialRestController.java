package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.OrderMaterial;
import kg.tezal.tezal_back.model.OrderMaterialModel;
import kg.tezal.tezal_back.service.OrderMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/order_material")
public class OrderMaterialRestController {

    private final OrderMaterialService materialService;

    public OrderMaterialRestController(OrderMaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/{id}")
    public OrderMaterial getOrderMaterialById(@PathVariable("id") Long id) {
        return materialService.findById(id);
    }
    @GetMapping("/model/{id}")
    public OrderMaterialModel getOrderMaterialModelById(@PathVariable("id") Long id) {
        return materialService.getById(id);
    }


    @GetMapping("/all")
    public List<OrderMaterialModel> getAll(@RequestParam("orderId")Long orderId) {
        return materialService.findAllByOrgId(orderId);
    }

    @PutMapping("/{id}")
    public OrderMaterial putOrderMaterial(@PathVariable("id") Long id, @RequestBody OrderMaterialModel orderMaterialModel) {
        return materialService.putById(id, orderMaterialModel);
    }

    @PostMapping()
    public OrderMaterialModel postOrderMaterial(@RequestBody OrderMaterialModel orderMaterialModel) {
        orderMaterialModel.setId(materialService.create(orderMaterialModel).getId());
        return orderMaterialModel;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return materialService.deleteById(id);
    }

}
