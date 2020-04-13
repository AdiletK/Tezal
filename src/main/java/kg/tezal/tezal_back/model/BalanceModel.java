package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceModel {
    private Long clientId;
    private String firstName;
    private String lastName;
    private Long balanceId;
    private Double amount;
    private String type;
}
