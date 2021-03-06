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
@Table(name = "sale")
public class Sale {

    @Id
    @SequenceGenerator(name = "sale_seq", sequenceName = "sale_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_seq")
    private Long id;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Float summ;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order orders;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;

}