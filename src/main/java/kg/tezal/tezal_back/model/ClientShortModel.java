package kg.tezal.tezal_back.model;

import kg.tezal.tezal_back.enums.ClientSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientShortModel {
    private Long id;
    private String image;
    private String firstName;
    private String lastName;
    private String patronymic;
    private ClientSex clientSex;
}
