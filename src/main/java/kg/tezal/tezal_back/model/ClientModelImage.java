package kg.tezal.tezal_back.model;

import kg.tezal.tezal_back.enums.ClientSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientModelImage {

    @NotNull
    private Long id;

    private MultipartFile image;

    private String personalCode;

    private String firstName;

    private String lastName;

    private String patronymic;

    private ClientSex clientSex;

    private String nationality;

    private String locale;
}
