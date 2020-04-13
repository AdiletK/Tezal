package kg.tezal.tezal_back.model;

import kg.tezal.tezal_back.enums.BonusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgBonusTypeModel {
    private Long id;
    private String name;
    private BonusType valueType;
    private String description;
}
