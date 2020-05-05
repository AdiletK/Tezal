package kg.tezal.tezal_back.model;

//<<<<<<< Updated upstream

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrganizationModelImage implements Serializable {
    private Long id;
    private MultipartFile image;
    private Set<MultipartFile> images;
    private Boolean status;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String description;
}
