package kg.tezal.tezal_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {
    @NotNull
    private Long id;

    private String image;

    @NotNull
    @Size(min = 5, max = 255)
    private String name;

    @NotNull
    private Date dateFrom;

    @NotNull
    private Date dateTo;
}
