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
public class RateModel {

    private Long id;
    @NotNull
    private  Float wholesalePrice;
    @NotNull
    private  Float retailPrice;
    @NotNull
    private  Float quantityInStock;

    private Long rawMaterialId;
    private String rawMaterialName;
    private String rawMaterialCategory;

    private Long organizationId;
    private String organizationName;

    private String rawMaterialImage;

    public RateModel(Long id, @NotNull Float wholesalePrice, @NotNull Float retailPrice, @NotNull Float quantityInStock, Long rawMaterialId, String rawMaterialName, String rawMaterialCategory, Long organizationId, String organizationName) {
        this.id = id;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.quantityInStock = quantityInStock;
        this.rawMaterialId = rawMaterialId;
        this.rawMaterialName = rawMaterialName;
        this.rawMaterialCategory = rawMaterialCategory;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }
}
