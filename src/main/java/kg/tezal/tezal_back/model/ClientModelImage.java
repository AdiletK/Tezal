package kg.tezal.tezal_back.model;

import kg.tezal.tezal_back.enums.ClientSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientModelImage {

    @NotNull
    private Long id;

    private MultipartFile image;

    @NotNull
    @Size(min = 5, max = 255)
    private String personalCode;

    @NotNull
    @Size(min = 5, max = 255)
    private String firstName;

    @NotNull
    @Size(min = 5, max = 255)
    private String lastName;

    @NotNull
    @Size(min = 5, max = 255)
    private String patronymic;

    @NotNull
    private ClientSex clientSex;

    @NotNull
    private String nationality;

    @NotNull
    private String locale;
}
