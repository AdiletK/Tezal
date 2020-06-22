package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialModelImage {
    private Long id;
    private String name;
    private MultipartFile image;
    private Long unitId;
    private String description;
    private Float volume;
    private int amountInBlock;
    private Long materialCategoryId;
    private String materialCategoryName;
}
