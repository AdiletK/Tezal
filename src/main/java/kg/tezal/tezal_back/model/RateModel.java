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

    private Long organizationId;
    private String organizationName;

}
