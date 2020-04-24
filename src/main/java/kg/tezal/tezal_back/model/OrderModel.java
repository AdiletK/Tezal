package kg.tezal.tezal_back.model;

import kg.tezal.tezal_back.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private Long id;

    private OrderStatus ordersStatus;

    private Long userId;

    private Long clientId;
    private String clientFirstName;
    private String clientLastName;

    private Long organizationId;
    private String organizationName;

    private LocalDateTime updatedDate;
}
