package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Order;
import kg.tezal.tezal_back.model.OrderModel;
import kg.tezal.tezal_back.model.SalesShortModel;
import kg.tezal.tezal_back.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/model/{id}")
    public OrderModel getOrderModelById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @GetMapping("/all")
    public List<OrderModel> getAll(@RequestParam("orgId")Long orgId) {
        return orderService.findAllByOrgId(orgId);
    }

    @PutMapping("/{id}")
    public Order putOrder(@PathVariable("id") Long id, @RequestBody OrderModel orderModel) {
        return orderService.putById(id, orderModel);
    }

    @GetMapping("/list/filter/{orgId}")
    public List<SalesShortModel> findAllDeliveredByOrgIdWithFilter(@PathVariable Long orgId, @RequestParam("dateFrom")String dateFrom,
                                                                   @RequestParam("dateTo") String dateTo){
        return orderService.findAllDeliveredByOrgIdWithDate(orgId, dateFrom, dateTo);
    }

    @PostMapping()
    public Order postOrder(@RequestBody OrderModel orderModel) {
        return orderService.create(orderModel);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return orderService.deleteById(id);
    }

}
