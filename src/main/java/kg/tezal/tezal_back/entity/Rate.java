package kg.tezal.tezal_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rate")
public class Rate  {

    @Id
    @SequenceGenerator(name = "rate_seq", sequenceName = "rate_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_seq")
    private Long id;

    @Column(nullable = false)
    private  Float wholesalePrice;

    @Column(nullable = false)
    private  Float retailPrice;

    @Column(nullable = false)
    private  Float quantityInStock;

    @ManyToOne
    @JoinColumn(name = "rawMaterial_id")
    private RawMaterial rawMaterial;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name="create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name="update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;


}
