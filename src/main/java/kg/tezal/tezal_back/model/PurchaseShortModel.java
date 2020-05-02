package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseShortModel {
    private String organizationName;
    private String rawMaterialName;
    private String supplierName;
    private Float priceForOne;
    private int count;
    private Float summ;
    private String date;
}
