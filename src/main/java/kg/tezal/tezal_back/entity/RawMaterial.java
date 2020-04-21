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
@Table(name = "rawMaterial")
public class RawMaterial {

    @Id
    @SequenceGenerator(name = "rawMaterial_seq", sequenceName = "rawMaterial_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rawMaterial_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float volume;

    @Column(nullable = false)
    private int amountInBlock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MaterialCategory materialCategory;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;
}