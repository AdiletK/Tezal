package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseModel {
    private Long id;
    @NotNull
    private int count;
    @NotNull
    private Float summ;
    private String publisher;
    private String barCode;
    @NotNull
    private Float priceForOne;

    private Long rawMaterialId;
    private String rawMaterialName;
    private Float volume;

    private Long organizationId;
    private String organizationName;

    private Long supplierId;
    private String supplierName;
}
