package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDeviceModel {
    private Long id;
    private String phoneNumber;
    private String password;
    private String imei;
    private Date lastEnterDate;
    private Boolean status;
}
