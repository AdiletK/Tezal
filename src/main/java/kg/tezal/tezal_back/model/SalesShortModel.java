package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesShortModel {
    private String firstName;
    private String LastName;
    private String organizationName;
    private String rawMaterialName;
    private int count;
    private Float summ;
    private String date;
}
