//package kg.tezal.tezal_back.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "budget")
//public class Budget {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(updatable = false, nullable = false)
//    private Long id;
//
//    @Column(nullable = false)
//    private Float amount;
//
//    @CreationTimestamp
//    @Column(updatable = false, nullable = false, name = "create_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
//    private LocalDateTime createDate;
//
//    @UpdateTimestamp
//    @Column(name = "update_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
//    private LocalDateTime updateDate;
//
//}
