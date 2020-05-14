package kg.tezal.tezal_back.model;

//<<<<<<< Updated upstream

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
//=======
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.web.multipart.MultipartFile;
//import javax.validation.constraints.Size;
//>>>>>>> Stashed changes


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizationModelImage {
    private Long id;
    private MultipartFile image;
    private Set<MultipartFile> images;
    private Boolean status;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String description;
}
