package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialModelImage {
    private Long id;
    @Size(min = 1, max = 255)
    private String name;
    private MultipartFile image;
    private Long unitId;
    @Size(min = 10, max = 255)
    private String description;
    private Float volume;
    private int amountInBlock;
    private Long materialCategoryId;
    private String materialCategoryName;
}
