package kg.tezal.tezal_back.model;


import kg.tezal.tezal_back.entity.OrgBonusType;
import kg.tezal.tezal_back.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BonusModel {
    private Long id;
    private OrgBonusType orgBonusType;
    private Organization organization;
    private Boolean status;
    private Date validFrom;
    private Date validTo;
    private Integer validity;
    private Date createdDate;
}
