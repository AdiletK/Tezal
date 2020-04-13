package kg.tezal.tezal_back.model;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String phone;
    private String password;
}
