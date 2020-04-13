package kg.tezal.tezal_back.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgCategoryTagModel {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
