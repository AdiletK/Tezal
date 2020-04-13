package kg.tezal.tezal_back.model;

import kg.tezal.tezal_back.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceHistoryModel {
    private Long clientId;
    private Long bonusId;
    private Long typeId;
    private Long userId;
    private Long orgId;
    private Double amount;
    private Double invoiceAmount;
    private String bill;
    private OperationType operationType;
}
