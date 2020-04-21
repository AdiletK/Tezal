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
@Table(name = "purchase")
public class Purchase {

    @Id
    @SequenceGenerator(name = "purchase_seq", sequenceName = "purchase_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq")
    private Long id;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Float summ;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String barCode;

    @Column(nullable = false)
    private Float priceForOne;

    @ManyToOne
    @JoinColumn(name = "rawMaterial_id")
    private RawMaterial rawMaterial;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;

}