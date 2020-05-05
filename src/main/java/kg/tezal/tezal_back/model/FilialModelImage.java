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
public class FilialModelImage {
    private Long id;
    private MultipartFile image;
    private Boolean status;
    private String address;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Long orgId;
    private Double averageRate;
}
