package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMaterialModel {
    private Long id;
    private Long orderId;

    private Double count;
    private Double sum;

    private Long rawMaterialId;
    private String rawMaterialName;
    private Float rawMaterialVolume;

}
