package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.BalanceHistory;
import kg.tezal.tezal_back.model.BalanceHistoryLongModel;
import kg.tezal.tezal_back.model.BalanceHistoryModel;
import kg.tezal.tezal_back.model.HistoryModel;
import kg.tezal.tezal_back.service.BalanceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/balanceHistory")
public class BalanceHistoryRestController {
    @Autowired
    private BalanceHistoryService balanceHistoryService;

    @GetMapping("/{id}")
    public BalanceHistory getBalanceHistoryById(@PathVariable("id") Long id) {
        return balanceHistoryService.findById(id);
    }
    @GetMapping("/client/{id}")
    public List<HistoryModel> getBalanceHistoryByClientId(@PathVariable("id") Long clientId,
                                                          @RequestParam(value = "last_d", defaultValue="2000-01-01 00:00:00", required = false) String last,
                                                          @RequestParam(value = "last_i", defaultValue = "0", required = false) Long hid,
                                                          @RequestParam(value = "limit", defaultValue = "7", required = false) Integer pageSize) {
        return balanceHistoryService.findHistoryByClientId(clientId, last, hid, pageSize);
    }

    @GetMapping("/user/{id}")
    public List<BalanceHistoryLongModel> getBalanceHistoryByUserId(@PathVariable("id") Long id,
                                                                   @RequestParam(value ="dateFrom", required = false)String dateFrom,
                                                                   @RequestParam(value ="dateFrom", required = false)String dateTo) {
        return balanceHistoryService.getCashierOperationHistory(id, dateFrom, dateTo);
    }

    @GetMapping("/all")
    public List<BalanceHistory> getAll() {
        return balanceHistoryService.findAll();
    }

    @PutMapping("/{id}")
    public BalanceHistory putBalanceHistory(@PathVariable("id") Long id, @RequestBody BalanceHistory balanceHistory) {
        return balanceHistoryService.putById(id, balanceHistory);
    }

//    @PostMapping()
//    public BalanceHistory postBalanceHistory(@RequestBody BalanceHistory balanceHistory) {
//        balanceHistoryService.create(balanceHistory);
//        return balanceHistory;
//    }
    @PostMapping()
    public BalanceHistory postBalanceHistory(@RequestBody BalanceHistoryModel balanceHistory) {
        return balanceHistoryService.create(balanceHistory);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return balanceHistoryService.deleteById(id);
    }

}
