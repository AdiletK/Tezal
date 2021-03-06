package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialShortModel {
    private Long id;
    private String name;
    private Float volume;
    private String image;
}
